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
	}

	private static boolean isUsable(ItemStack stack) {
		return stack.getDamage() < stack.getMaxDamage() - 1;
	}
}