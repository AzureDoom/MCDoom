package mod.azure.doom.util;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.powerup.SoulCubeItem;
import mod.azure.doom.item.weapons.AxeMarauderItem;
import mod.azure.doom.item.weapons.Chainsaw;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.doom.item.weapons.DoomBaseItem;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoomMod.MODID)
public class ServerEvents {

	@SubscribeEvent
	public static void anvilEvent(AnvilUpdateEvent event) {
		if ((event.getLeft().getItem() instanceof DoomBaseItem || event.getLeft().getItem() instanceof AxeMarauderItem || event.getLeft().getItem() instanceof SwordCrucibleItem || event.getLeft().getItem() instanceof DarkLordCrucibleItem || event.getLeft().getItem() instanceof SentinelHammerItem || event.getLeft().getItem() instanceof Chainsaw || event.getLeft().getItem() instanceof ChainsawAnimated || event.getLeft().getItem() instanceof SoulCubeItem)
				&& (EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.MENDING) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.UNBREAKING) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.INFINITY_ARROWS) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.FLAMING_ARROWS) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.PUNCH_ARROWS))) {
			event.setCanceled(true);
		}
	}

}