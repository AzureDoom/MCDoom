package mod.azure.doom.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class DoomSpecialCraftingRecipe implements GunRecipes {
	private final ResourceLocation id;

	public DoomSpecialCraftingRecipe(ResourceLocation id) {
		this.id = id;
	}

	public ResourceLocation getId() {
		return this.id;
	}

	public boolean isIgnoredInRecipeBook() {
		return true;
	}

	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}
}