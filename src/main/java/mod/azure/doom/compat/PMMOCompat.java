package mod.azure.doom.compat;

import harmonised.pmmo.api.APIUtils;
import mod.azure.doom.config.DoomConfig;
import net.minecraft.server.level.ServerPlayer;

public class PMMOCompat {

	public static void awardInmortalXp(ServerPlayer player) {
		APIUtils.addXp("crafting", player.getUUID(), DoomConfig.SERVER.inmortal_consume_xp_pmmo.get(), null, true,
				false);
	}

	public static void awardInvisibleXp(ServerPlayer player) {
		APIUtils.addXp("crafting", player.getUUID(), DoomConfig.SERVER.invisible_consume_xp_pmmo.get(), null, true,
				false);
	}

	public static void awardMegaXp(ServerPlayer player) {
		APIUtils.addXp("crafting", player.getUUID(), DoomConfig.SERVER.mega_consume_xp_pmmo.get(), null, true, false);
	}

	public static void awardPowerXp(ServerPlayer player) {
		APIUtils.addXp("crafting", player.getUUID(), DoomConfig.SERVER.power_consume_xp_pmmo.get(), null, true, false);
	}

	public static void awardSoulXp(ServerPlayer player) {
		APIUtils.addXp("crafting", player.getUUID(), DoomConfig.SERVER.soul_consume_xp_pmmo.get(), null, true, false);
	}

	public static void awardCrafting(ServerPlayer player) {
		APIUtils.addXp("crafting", player.getUUID(), DoomConfig.SERVER.guntable_crafting_xp_pmmo.get(), null, true,
				false);
	}

}