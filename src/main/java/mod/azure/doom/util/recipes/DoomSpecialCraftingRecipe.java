package mod.azure.doom.util.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class DoomSpecialCraftingRecipe implements GunRecipes {
	private final Identifier id;

	public DoomSpecialCraftingRecipe(Identifier id) {
	      this.id = id;
	   }

	public Identifier getId() {
		return this.id;
	}

	public boolean isIgnoredInRecipeBook() {
		return true;
	}

	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}
}