package mod.azure.doom.client;

import mod.azure.doom.util.registry.DoomItems;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModelProviderinit {

	public static void init() {
		// Crucible
		FabricModelPredicateProviderRegistry.register(DoomItems.CRUCIBLESWORD, new Identifier("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return isUsable(itemStack) ? 0.0F : 1.0F;
				});
		// Marauder Axe
		FabricModelPredicateProviderRegistry.register(DoomItems.AXE_OPEN, new Identifier("broken"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return itemStack.getDamage() < itemStack.getMaxDamage() - 1 ? 0.0F : 1.0F;
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.AXE_OPEN, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					return 0.0F;
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.SG, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.SSG, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.UNMAYKR, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.ROCKETLAUNCHER, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.PLASMAGUN, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.PISTOL, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.HEAVYCANNON, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
		FabricModelPredicateProviderRegistry.register(DoomItems.BFG_ETERNAL, new Identifier("pull"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity == null) {
						return 0.0F;
					} else {
						return 0.0F;
					}
				});
	}

	private static boolean isUsable(ItemStack stack) {
		return stack.getDamage() < stack.getMaxDamage() - 1;
	}
}