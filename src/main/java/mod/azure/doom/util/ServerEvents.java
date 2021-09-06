package mod.azure.doom.util;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.AxeMarauderItem;
import mod.azure.doom.item.weapons.Chainsaw;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.doom.item.weapons.DoomBaseItem;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoomMod.MODID)
public class ServerEvents {

	@SubscribeEvent
	public static void anvilEvent(AnvilUpdateEvent event) {
		if ((event.getLeft().getItem() instanceof DoomBaseItem)
				|| (event.getLeft().getItem() instanceof AxeMarauderItem)
				|| (event.getLeft().getItem() instanceof SwordCrucibleItem)
				|| (event.getLeft().getItem() instanceof DarkLordCrucibleItem)
				|| (event.getLeft().getItem() instanceof SentinelHammerItem)
				|| (event.getLeft().getItem() instanceof Chainsaw)
				|| (event.getLeft().getItem() instanceof ChainsawAnimated)
						&& event.getRight().getItem() == Items.ENCHANTED_BOOK) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void anvilEvent(EnchantmentLevelSetEvent event) {
		if ((event.getItem().getItem() instanceof DoomBaseItem)
				|| (event.getItem().getItem() instanceof AxeMarauderItem)
				|| (event.getItem().getItem() instanceof SwordCrucibleItem)
				|| (event.getItem().getItem() instanceof DarkLordCrucibleItem)
				|| (event.getItem().getItem() instanceof SentinelHammerItem)
				|| (event.getItem().getItem() instanceof Chainsaw)
				|| (event.getItem().getItem() instanceof ChainsawAnimated)) {
			event.setCanceled(true);
		}
	}

}