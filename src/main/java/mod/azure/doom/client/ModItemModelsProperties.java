package mod.azure.doom.client;

import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModItemModelsProperties {

	public static void init() {
		// Crucible
		ItemModelsProperties.register(DoomItems.CRUCIBLESWORD.get(), new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// Marauder Axe
		ItemModelsProperties.register(DoomItems.AXE_OPEN.get(), new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// NonCenter
		ItemModelsProperties.register(DoomItems.SG.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.ROCKETLAUNCHER.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.PLASMAGUN.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.HEAVYCANNON.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.UNMAYKR.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.CHAINGUN.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.BFG_ETERNAL.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.BALLISTA.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.SSG.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemModelsProperties.register(DoomItems.PISTOL.get(), new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}

	private static boolean nonCentered(ItemStack stack) {
		return DoomConfig.SERVER.enable_noncenter.get();
	}
}