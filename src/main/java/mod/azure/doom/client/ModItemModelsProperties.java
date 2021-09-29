package mod.azure.doom.client;

import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModItemModelsProperties {

	public static void init() {
		// Crucible
		ItemModelsProperties.register(DoomItems.CRUCIBLESWORD.get(), new ResourceLocation("broken"),
				(p_210312_0_, p_210312_1_, p_210312_2_) -> {
					return isUsable(p_210312_0_) ? 0.0F : 1.0F;
				});
		// Marauder Axe
		ItemModelsProperties.register(DoomItems.AXE_OPEN.get(), new ResourceLocation("broken"),
				(p_210312_0_, p_210312_1_, p_210312_2_) -> {
					return isUsable(p_210312_0_) ? 0.0F : 1.0F;
				});
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}
}