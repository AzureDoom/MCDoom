package mod.azure.doom.recipes;

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
import mod.azure.doom.client.gui.GunTableInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class GunTableRecipe implements Recipe<GunTableInventory>, Comparable<GunTableRecipe> {

	public static final RecipeType<GunTableRecipe> GUN_TABLE = RecipeType
			.register(new Identifier(DoomMod.MODID, "gun_table").toString());

	private final Identifier id;
	private final Pair<Ingredient, Integer>[] ingredients;
	private final ItemStack output;

	public GunTableRecipe(Identifier id, Pair<Ingredient, Integer>[] ingredients, ItemStack output) {
		this.id = id;
		this.ingredients = ingredients;
		this.output = output;
	}

	@Override
	public boolean matches(GunTableInventory inv, World world) {
		for (int i = 0; i < 5; i++) {
			ItemStack slotStack = inv.getStack(i);
			Pair<Ingredient, Integer> pair = ingredients[i];
			Ingredient ingredient = pair.getLeft();
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
	public ItemStack craft(GunTableInventory inv) {
		return this.getOutput().copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return DoomMod.GUN_TABLE_RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return GUN_TABLE;
	}

	@Override
	public int compareTo(@NotNull GunTableRecipe o) {
		Item outputThis = getOutput().getItem();
		Item outputOther = o.getOutput().getItem();
		return Registry.ITEM.getId(outputThis).compareTo(Registry.ITEM.getId(outputOther));
	}

	public static class Serializer implements RecipeSerializer<GunTableRecipe> {

		@SuppressWarnings("unchecked")
		public GunTableRecipe read(Identifier identifier, JsonObject jsonObject) {

			String pattern = JsonHelper.getString(jsonObject, "pattern");
			Map<String, Pair<Ingredient, Integer>> map = getComponents(JsonHelper.getObject(jsonObject, "key"));

			List<Pair<Ingredient, Integer>> pairList = getIngredients(pattern, map, pattern.length());
			if (pairList.isEmpty()) {
				throw new JsonParseException("No ingredients for gun table recipe");
			} else if (pairList.size() > 5) {
				throw new JsonParseException("Too many ingredients for gun table recipe");
			} else {
				ItemStack itemStack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"));
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

		@SuppressWarnings("unchecked")
		public GunTableRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
			Pair<Ingredient, Integer>[] pairs = new Pair[5];
			for (int j = 0; j < 5; ++j) {
				Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
				int count = packetByteBuf.readInt();
				pairs[j] = Pair.of(ingredient, count);
			}

			ItemStack output = packetByteBuf.readItemStack();
			return new GunTableRecipe(identifier, pairs, output);
		}

		public void write(PacketByteBuf packetByteBuf, GunTableRecipe gunTableRecipe) {
			for (int i = 0; i < 5; i++) {
				Pair<Ingredient, Integer> pair = gunTableRecipe.ingredients[i];
				Ingredient ingredient = pair.getLeft();
				int count = pair.getRight();
				ingredient.write(packetByteBuf);
				packetByteBuf.writeInt(count);
			}
			packetByteBuf.writeItemStack(gunTableRecipe.output);
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
						JsonHelper.getInt(jsonElement.getAsJsonObject(), "count", 1)));
			}

			map.put(" ", Pair.of(Ingredient.EMPTY, 0));
			return map;
		}
	}
}
