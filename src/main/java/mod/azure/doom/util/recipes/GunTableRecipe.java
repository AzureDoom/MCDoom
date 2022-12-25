package mod.azure.doom.util.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.DoomGunInventory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class GunTableRecipe implements Recipe<DoomGunInventory>, Comparable<GunTableRecipe> {

	public static ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(DoomMod.MODID, "guns");
	public final ResourceLocation id;
	public final Pair<Ingredient, Integer>[] ingredients;
	public final ItemStack output;

	public GunTableRecipe(ResourceLocation id, Pair<Ingredient, Integer>[] ingredients, ItemStack output) {
		this.id = id;
		this.ingredients = ingredients;
		this.output = output;
	}

	@Override
	public boolean matches(DoomGunInventory inv, Level world) {
		for (int i = 0; i < 5; i++) {
			ItemStack slotStack = inv.getItem(i);
			Pair<Ingredient, Integer> pair = ingredients[i];
			Ingredient ingredient = pair.getKey();
			int count = pair.getRight();
			if (slotStack.getCount() < count || !(ingredient.test(slotStack))) {
				return false;
			}
		}
		return true;
	}

	public Ingredient getIngredientForSlot(int index) {
		return ingredients[index].getLeft();
	}

	public int countRequired(int index) {
		return ingredients[index].getRight();
	}

	@Override
	public ItemStack assemble(DoomGunInventory inv) {
		return this.getResultItem().copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return output;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return DoomMod.GUN_TABLE_RECIPE_SERIALIZER;
	}

	public static class Type implements RecipeType<GunTableRecipe> {
		private Type() {
		}
		
		public static final Type INSTANCE = new Type();
		public static final String ID = "gun_table";
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	@Override
	public int compareTo(@NotNull GunTableRecipe o) {
		Item outputThis = getResultItem().getItem();
		Item outputOther = o.getResultItem().getItem();
		return BuiltInRegistries.ITEM.getKey(outputThis).compareTo(BuiltInRegistries.ITEM.getKey(outputOther));
	}
	
	public static class Serializer implements RecipeSerializer<GunTableRecipe> {

		@Override
		public GunTableRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject) {
			String pattern = GsonHelper.getAsString(jsonObject, "pattern");
			Map<String, Pair<Ingredient, Integer>> map = getComponents(GsonHelper.getAsJsonObject(jsonObject, "key"));

			List<Pair<Ingredient, Integer>> pairList = getIngredients(pattern, map, pattern.length());
			if (pairList.isEmpty()) {
				throw new JsonParseException("No ingredients for gun table recipe");
			} else if (pairList.size() > 5) {
				throw new JsonParseException("Too many ingredients for gun table recipe");
			} else {
				ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
				return new GunTableRecipe(identifier, pairList.toArray(new Pair[0]), itemStack);
			}
		}

		private static List<Pair<Ingredient, Integer>> getIngredients(String pattern,
				Map<String, Pair<Ingredient, Integer>> keys, int width) {
			List<Pair<Ingredient, Integer>> pairList = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				pairList.add(Pair.of(Ingredient.EMPTY, 0));
			}
			Set<String> set = Sets.newHashSet(keys.keySet());
			set.remove(" ");

			for (int i = 0; i < pattern.length(); ++i) {
				String key = pattern.substring(i, i + 1);
				Ingredient ingredient = keys.get(key).getKey();
				if (ingredient == null) {
					throw new JsonSyntaxException(
							"Pattern references symbol '" + key + "' but it's not defined in the key");
				}

				set.remove(key);
				pairList.set(i, Pair.of(ingredient, keys.get(key).getRight()));
			}

			if (!set.isEmpty()) {
				throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
			} else {
				return pairList;
			}
		}

		@Override
		public GunTableRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf packetByteBuf) {
			Pair<Ingredient, Integer>[] pairs = new Pair[5];
			for (int j = 0; j < 5; ++j) {
				Ingredient ingredient = Ingredient.fromNetwork(packetByteBuf);
				int count = packetByteBuf.readInt();
				pairs[j] = Pair.of(ingredient, count);
			}

			ItemStack output = packetByteBuf.readItem();
			return new GunTableRecipe(identifier, pairs, output);
		}

		@Override
		public void toNetwork(FriendlyByteBuf packetByteBuf, GunTableRecipe gunTableRecipe) {
			for (int i = 0; i < 5; i++) {
				Pair<Ingredient, Integer> pair = gunTableRecipe.ingredients[i];
				Ingredient ingredient = pair.getKey();
				int count = pair.getRight();
				ingredient.toNetwork(packetByteBuf);
				packetByteBuf.writeInt(count);
			}
			packetByteBuf.writeItem(gunTableRecipe.output);
		}

		private static Map<String, Pair<Ingredient, Integer>> getComponents(JsonObject json) {
			Map<String, Pair<Ingredient, Integer>> map = Maps.newHashMap();

			for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
				String key = entry.getKey();
				JsonElement jsonElement = entry.getValue();
				if (key.length() != 1) {
					throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey()
							+ "' is an invalid symbol (must be 1 String only).");
				}

				if (" ".equals(key)) {
					throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
				}

				map.put(key, Pair.of(Ingredient.fromJson(jsonElement),
						GsonHelper.getAsInt(jsonElement.getAsJsonObject(), "count", 1)));
			}

			map.put(" ", Pair.of(Ingredient.EMPTY, 0));
			return map;
		}
	}
}
