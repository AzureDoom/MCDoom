package mod.azure.doom.client;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModelProviderinit {

	public static void init() {
		// Crucible
		ItemProperties.register(DoomItems.CRUCIBLESWORD, new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// Marauder Axe
		ItemProperties.register(DoomItems.AXE_OPEN, new ResourceLocation("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return itemStack.getDamageValue() < itemStack.getMaxDamage() - 1 ? 0.0F : 1.0F;
				});
		// NonCenter
		ItemProperties.register(DoomItems.SG, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.ROCKETLAUNCHER, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.PLASMAGUN, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.HEAVYCANNON, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.UNMAYKR, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.UNMAKER, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.CHAINGUN, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.BFG_ETERNAL, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.BALLISTA, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.SSG, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.PISTOL, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.DPLASMARIFLE, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.DGAUSS, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.DSG, new ResourceLocation("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ItemProperties.register(DoomItems.CHAINSAW, new ResourceLocation("stalled"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		ItemProperties.register(DoomItems.CHAINSAW64, new ResourceLocation("stalled"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
	}

	private static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}

	private static boolean nonCentered(ItemStack stack) {
		return DoomConfig.enable_noncenter;
	}
}