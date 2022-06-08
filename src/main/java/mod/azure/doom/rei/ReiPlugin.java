package mod.azure.doom.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.forge.REIPlugin;
import mod.azure.doom.DoomMod;
import mod.azure.doom.recipes.GunTableRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

@REIPlugin(value = Dist.CLIENT)
public class ReiPlugin implements REIClientPlugin {

	public static final CategoryIdentifier<DoomDisplay> CRAFTING = CategoryIdentifier
			.of(new ResourceLocation(DoomMod.MODID, "crafting"));

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
