package mod.azure.doom.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class GunRecipeSerializer implements RecipeSerializer<GunTableRecipe> {

	private static List<Pair<Ingredient, Integer>> getIngredients(String pattern, Map<String, Pair<Ingredient, Integer>> keys, int width) {
		final List<Pair<Ingredient, Integer>> pairList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			pairList.add(Pair.of(Ingredient.EMPTY, 0));
		}
		final Set<String> set = Sets.newHashSet(keys.keySet());
		set.remove(" ");

		for (int i = 0; i < pattern.length(); ++i) {
			final String key = pattern.substring(i, i + 1);
			final Ingredient ingredient = keys.get(key).getKey();
			if (ingredient == null) {
				throw new JsonSyntaxException("Pattern references symbol '" + key + "' but it's not defined in the key");
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

	private static Map<String, Pair<Ingredient, Integer>> getComponents(JsonObject json) {
		final Map<String, Pair<Ingredient, Integer>> map = Maps.newHashMap();

		for (final Map.Entry<String, JsonElement> entry : json.entrySet()) {
			final String key = entry.getKey();
			final JsonElement jsonElement = entry.getValue();
			if (key.length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 String only).");
			}

			if (" ".equals(key)) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map.put(key, Pair.of(Ingredient.fromJson(jsonElement), GsonHelper.getAsInt(jsonElement.getAsJsonObject(), "count", 1)));
		}

		map.put(" ", Pair.of(Ingredient.EMPTY, 0));
		return map;
	}

	@Override
	public GunTableRecipe fromJson(ResourceLocation ResourceLocation, JsonObject jsonObject) {

		final String pattern = GsonHelper.getAsString(jsonObject, "pattern");
		final Map<String, Pair<Ingredient, Integer>> map = getComponents(GsonHelper.getAsJsonObject(jsonObject, "key"));

		final List<Pair<Ingredient, Integer>> pairList = getIngredients(pattern, map, pattern.length());
		if (pairList.isEmpty()) {
			throw new JsonParseException("No ingredients for gun table recipe");
		} else if (pairList.size() > 5) {
			throw new JsonParseException("Too many ingredients for gun table recipe");
		} else {
			final ItemStack itemStack = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(jsonObject, "result")).getDefaultInstance();
			return new GunTableRecipe(ResourceLocation, pairList.toArray(new Pair[0]), itemStack);
		}
	}

	@Override
	public void toNetwork(FriendlyByteBuf packetbuffer, GunTableRecipe gunTableRecipe) {
		for (int i = 0; i < 5; i++) {
			final Pair<Ingredient, Integer> pair = gunTableRecipe.ingredients[i];
			final Ingredient ingredient = pair.getLeft();
			final int count = pair.getRight();
			ingredient.toNetwork(packetbuffer);
			packetbuffer.writeInt(count);
		}
		packetbuffer.writeItem(gunTableRecipe.output);
	}

	@Override
	public GunTableRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf packetbuffer) {
		final Pair<Ingredient, Integer>[] pairs = new Pair[5];
		for (int j = 0; j < 5; ++j) {
			final Ingredient ingredient = Ingredient.fromNetwork(packetbuffer);
			final int count = packetbuffer.readInt();
			pairs[j] = Pair.of(ingredient, count);
		}

		final ItemStack output = packetbuffer.readItem();
		return new GunTableRecipe(resourceLocation, pairs, output);
	}
}
