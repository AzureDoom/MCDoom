package mod.azure.doom.recipes;

import mod.azure.doom.client.gui.weapons.DoomGunInventory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface GunRecipes extends Recipe<DoomGunInventory> {
	default RecipeType<?> getType() {
		return RecipeType.CRAFTING;
	}
}