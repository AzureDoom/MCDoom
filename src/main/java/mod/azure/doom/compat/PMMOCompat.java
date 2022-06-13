package mod.azure.doom.compat;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.server.level.ServerPlayer;

public class PMMOCompat {

	/*
	 * Awards magic xp if Project MMO is installed, configurable via config.
	 */
	public static void awardMagicXp(ServerPlayer player, int xpAmount) {
		APIUtils.addXp("magic", player.getUUID(), xpAmount, null, true, false);
	}

	/*
	 * Awards crafting xp if Project MMO is installed, configurable via config.
	 */
	public static void awardCrafting(ServerPlayer player, int xpAmount) {
		APIUtils.addXp("crafting", player.getUUID(), xpAmount, null, true, false);
	}

}