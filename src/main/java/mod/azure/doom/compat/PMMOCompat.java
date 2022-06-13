package mod.azure.doom.compat;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;

public class PMMOCompat {

	/*
	 * Awards magic xp if Project MMO is installed, configurable via config.
	 */
	public static void awardMagicXp(Player player, int xpAmount) {
		APIUtils.addXp("magic", player, xpAmount);
	}

	/*
	 * Awards crafting xp if Project MMO is installed, configurable via config.
	 */
	public static void awardCrafting(Player player, int xpAmount) {
		APIUtils.addXp("craft", player, xpAmount);
	}

}