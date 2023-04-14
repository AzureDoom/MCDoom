package mod.azure.doom.recipes;

import org.apache.commons.lang3.tuple.Pair;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.DoomGunInventory;
import mod.azure.doom.util.registry.DoomRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

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
			final ItemStack slotStack = inv.getItem(i);
			final Pair<Ingredient, Integer> pair = ingredients[i];
			final Ingredient ingredient = pair.getLeft();
			final int count = pair.getRight();
			if (slotStack.getCount() < count || !ingredient.test(slotStack)) {
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
	public ItemStack assemble(DoomGunInventory inv, RegistryAccess var2) {
		return getResultItem(var2).copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess var1) {
		return output;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return DoomRecipes.GUN_TABLE_RECIPE_SERIALIZER.get();
	}

	public static class Type implements RecipeType<GunTableRecipe> {
		public static final Type INSTANCE = new Type();
		public static final String ID = "gun_table";
	}

	@Override
	public RecipeType<?> getType() {
		return Type.INSTANCE;
	}

	@Override
	public int compareTo(GunTableRecipe o) {
		final var outputThis = output.getItem();
		final var outputOther = o.output.getItem();
		return ForgeRegistries.ITEMS.getKey(outputThis).compareTo(ForgeRegistries.ITEMS.getKey(outputOther));
	}
}
