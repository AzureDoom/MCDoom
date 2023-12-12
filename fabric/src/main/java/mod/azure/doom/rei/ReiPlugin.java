package mod.azure.doom.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mod.azure.doom.MCDoom;
import mod.azure.doom.recipes.GunTableRecipe;

public class ReiPlugin implements REIClientPlugin {

    public static final CategoryIdentifier<DoomDisplay> CRAFTING = CategoryIdentifier.of(
            MCDoom.modResource("crafting"));

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new DoomCategory());
        registry.addWorkstations(CRAFTING, DoomCategory.ICON);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(GunTableRecipe.class, DoomDisplay::new);
    }
}
