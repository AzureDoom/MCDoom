package mod.azure.doom.helper;

import mod.azure.doom.MCDoom;
import mod.azure.doom.items.powerup.SoulCubeItem;
import mod.azure.doom.items.weapons.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MCDoom.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        if ((event.getLeft().getItem() instanceof DoomBaseItem || event.getLeft().getItem() instanceof AxeMarauderItem || event.getLeft().getItem() instanceof SwordCrucibleItem || event.getLeft().getItem() instanceof DarkLordCrucibleItem || event.getLeft().getItem() instanceof SentinelHammerItem || event.getLeft().getItem() instanceof Chainsaw || event.getLeft().getItem() instanceof ChainsawAnimated || event.getLeft().getItem() instanceof SoulCubeItem) && (EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.MENDING) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.UNBREAKING) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.INFINITY_ARROWS) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.FLAMING_ARROWS) || EnchantmentHelper.getEnchantments(event.getRight()).containsKey(Enchantments.PUNCH_ARROWS))) {
            event.setCanceled(true);
        }
    }

}