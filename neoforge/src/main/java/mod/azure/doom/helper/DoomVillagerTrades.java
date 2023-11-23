package mod.azure.doom.helper;

import mod.azure.doom.MCDoom;
import mod.azure.doom.registry.NeoDoomItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;

public record DoomVillagerTrades() {

    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.WEAPONSMITH && MCDoom.config.enable_weaponsmith_trades) {
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 1, 1, NeoDoomItems.ARGENT_ENERGY.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.BULLETS.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 3, NeoDoomItems.CHAINGUN_BULLETS.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 3, NeoDoomItems.SHOTGUN_SHELLS.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 3, NeoDoomItems.ARGENT_BOLT.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 3, NeoDoomItems.ROCKET.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 3, NeoDoomItems.ENERGY_CELLS.get(), 6, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 3, NeoDoomItems.UNMAKRY_BOLT.get(), 6, 12, 5));
        }
        if (event.getType() == VillagerProfession.TOOLSMITH && MCDoom.config.enable_toolsmith_trades) {
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.ARGENT_PICKAXE.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.ARGENT_AXE.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.ARGENT_HOE.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.ARGENT_SHOVEL.get(), 1, 12, 5));
        }
        if (event.getType() == VillagerProfession.MASON && MCDoom.config.enable_mason_trades) {
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_1.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_2.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_3.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_4.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_5.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_6.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_7.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_8.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_9.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_10.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_11.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_12.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_13.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_14.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_15.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_16.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_17.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_18.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_19.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_20.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_21.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_22.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_23.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_24.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_25.get(), 1, 12, 5));
            event.getTrades().get(3).add(new VillagerTrades.ItemsAndEmeraldsToItems(Items.EMERALD, 2, NeoDoomItems.E1M1_26.get(), 1, 12, 5));
        }
    }

}
