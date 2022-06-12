package mod.azure.doom.compat;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.JType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class PMMOCompat {

	public static void awardInmortalXp(ServerPlayer player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.inmortal", null, true, true);
	}

	public static void awardInvisibleXp(ServerPlayer player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.invisible", null, true, true);
	}

	public static void awardMegaXp(ServerPlayer player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.mega", null, true, true);
	}

	public static void awardPowerXp(ServerPlayer player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.power", null, true, true);
	}

	public static void awardSoulXp(ServerPlayer player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.soul", null, true, true);
	}

	public static void awardCrafting(ItemStack itemStack) {
		APIUtils.getXp(itemStack, JType.XP_VALUE_CRAFT);
	}

}