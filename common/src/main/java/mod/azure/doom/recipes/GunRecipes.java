package mod.azure.doom.recipes;

import mod.azure.doom.client.gui.DoomGunInventory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface GunRecipes extends Recipe<DoomGunInventory> {
    @Override
    default RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }
}