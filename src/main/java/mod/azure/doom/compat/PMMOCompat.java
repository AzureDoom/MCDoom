package mod.azure.doom.compat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PMMOCompat {

	/*
	 * Awards magic xp if Project MMO is installed, configurable via config.
	 */
	public static void awardMagicXp(Player player, ItemStack stack) {
//		MinecraftForge.EVENT_BUS.post(new EatFoodEvent(player, stack));
	}

	/*
	 * Awards crafting xp if Project MMO is installed, configurable via config.
	 */
	public static void awardCrafting(Player player, int xpAmount) {
//		APIUtils.addXp("craft", player, xpAmount);
	}

}