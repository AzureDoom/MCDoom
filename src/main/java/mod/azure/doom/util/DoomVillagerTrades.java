package mod.azure.doom.util;

import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;

public class DoomVillagerTrades {

	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		if (event.getType() == VillagerProfession.WEAPONSMITH && DoomConfig.SERVER.enable_weaponsmith_trades.get()) {
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 1, 1,
					DoomItems.ARGENT_ENERGY.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.BULLETS.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 3,
					DoomItems.CHAINGUN_BULLETS.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 3,
					DoomItems.SHOTGUN_SHELLS.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 3,
					DoomItems.ARGENT_BOLT.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 3,
					DoomItems.ROCKET.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 3,
					DoomItems.ENERGY_CELLS.get(), 6, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 3,
					DoomItems.UNMAKRY_BOLT.get(), 6, 12, 5));
		}
		if (event.getType() == VillagerProfession.TOOLSMITH && DoomConfig.SERVER.enable_toolsmith_trades.get()) {
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.ARGENT_PICKAXE.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.ARGENT_AXE.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.ARGENT_HOE.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.ARGENT_SHOVEL.get(), 1, 12, 5));
		}
		if (event.getType() == VillagerProfession.MASON && DoomConfig.SERVER.enable_mason_trades.get()) {
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_1.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_2.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_3.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_4.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_5.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_6.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_7.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_8.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_9.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_10.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_11.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_12.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_13.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_14.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_15.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_16.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_17.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_18.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_19.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_20.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_21.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_22.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_23.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_24.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_25.get(), 1, 12, 5));
			event.getTrades().get(3).add(new VillagerTrades.ItemsForEmeraldsAndItemsTrade(Items.EMERALD, 2,
					DoomItems.E1M1_26.get(), 1, 12, 5));
		}
	}

}
