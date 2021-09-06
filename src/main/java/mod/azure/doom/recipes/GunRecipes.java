package mod.azure.doom.recipes;

import mod.azure.doom.client.gui.weapons.DoomGunInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface GunRecipes extends IRecipe<DoomGunInventory> {
	default IRecipeType<?> getType() {
		return IRecipeType.CRAFTING;
	}
}