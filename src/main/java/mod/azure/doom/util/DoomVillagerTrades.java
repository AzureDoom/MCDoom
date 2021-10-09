package mod.azure.doom.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

public class DoomVillagerTrades {

	public static void addTrades() {
		if (DoomMod.config.misc.enable_weaponsmith_trades) {
		List<TradeOffers.Factory> weapon_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).get(3)));
		TradeOffers.Factory[] result = new TradeOffers.Factory[] {};
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 1, 1, DoomItems.ARGENT_ENERGY, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomItems.BULLETS, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 3, DoomItems.CHAINGUN_BULLETS, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 3, DoomItems.SHOTGUN_SHELLS, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 3, DoomItems.ARGENT_BOLT, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 3, DoomItems.ROCKET, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 3, DoomItems.ENERGY_CELLS, 6, 12, 5));
		weapon_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 3, DoomItems.UNMAKRY_BOLT, 6, 12, 5));
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH).put(3,
				weapon_trades.toArray(result));
		}

		if (DoomMod.config.misc.enable_toolsmith_trades) {
		List<TradeOffers.Factory> tool_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.TOOLSMITH).get(3)));
		TradeOffers.Factory[] result = new TradeOffers.Factory[] {};
		tool_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomItems.ARGENT_PICKAXE, 1, 12, 5));
		tool_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomItems.ARGENT_AXE, 1, 12, 5));
		tool_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomItems.ARGENT_HOE, 1, 12, 5));
		tool_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomItems.ARGENT_SHOVEL, 1, 12, 5));
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.TOOLSMITH).put(3,
				tool_trades.toArray(result));
		}

		if (DoomMod.config.misc.enable_mason_trades) {
		List<TradeOffers.Factory> block_trades = new ArrayList<>(
				Arrays.asList(TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.MASON).get(3)));
		TradeOffers.Factory[] result = new TradeOffers.Factory[] {};
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_1.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_2.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_3.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_4.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_5.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_6.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_7.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_8.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_9.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_10.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_11.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_12.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_13.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_14.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_15.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_16.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_17.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_18.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_19.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_20.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_21.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_22.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_23.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_24.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_25.asItem(), 1, 12, 5));
		block_trades.add(new TradeOffers.ProcessItemFactory(Items.EMERALD, 2, DoomBlocks.E1M1_26.asItem(), 1, 12, 5));
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.MASON).put(3,
				block_trades.toArray(result));
		}
	}
}