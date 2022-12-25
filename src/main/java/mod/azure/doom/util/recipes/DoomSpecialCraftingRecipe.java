package mod.azure.doom.util.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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