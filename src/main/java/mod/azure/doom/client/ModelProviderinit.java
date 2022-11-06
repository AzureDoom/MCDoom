package mod.azure.doom.client;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModelProviderinit {

	public static void init() {
		// Crucible
		ModelPredicateProviderRegistry.register(DoomItems.CRUCIBLESWORD, new Identifier("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// Marauder Axe
		ModelPredicateProviderRegistry.register(DoomItems.AXE_OPEN, new Identifier("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return itemStack.getDamage() < itemStack.getMaxDamage() - 1 ? 0.0F : 1.0F;
				});
		// NonCenter
		ModelPredicateProviderRegistry.register(DoomItems.SG, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.ROCKETLAUNCHER, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.PLASMAGUN, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.HEAVYCANNON, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.UNMAYKR, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.UNMAKER, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.CHAINGUN, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.BFG_ETERNAL, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.BALLISTA, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.SSG, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.PISTOL, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.DPLASMARIFLE, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.DGAUSS, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.DSG, new Identifier("nocenter"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return nonCentered(itemStack) ? 1.0F : 0.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.CHAINSAW, new Identifier("stalled"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		ModelPredicateProviderRegistry.register(DoomItems.CHAINSAW64, new Identifier("stalled"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
	}

	private static boolean isUsable(ItemStack stack) {
		return stack.getDamage() < stack.getMaxDamage() - 1;
	}

	private static boolean nonCentered(ItemStack stack) {
		return DoomConfig.enable_noncenter;
	}
}