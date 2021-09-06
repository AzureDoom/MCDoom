package mod.azure.doom.util;

import harmonised.pmmo.api.APIUtils;
import harmonised.pmmo.config.JType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class PMMOCompat {

	public static void awardInmortalXp(ServerPlayerEntity player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.inmortal", null, true, true);
	}

	public static void awardInvisibleXp(ServerPlayerEntity player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.invisible", null, true, true);
	}

	public static void awardMegaXp(ServerPlayerEntity player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.mega", null, true, true);
	}

	public static void awardPowerXp(ServerPlayerEntity player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.power", null, true, true);
	}

	public static void awardSoulXp(ServerPlayerEntity player) {
		APIUtils.awardXpTrigger(player.getUUID(), "doomweapon.consume.soul", null, true, true);
	}

	public static void awardCrafting(ItemStack itemStack) {
		APIUtils.getXp(itemStack, JType.XP_VALUE_CRAFT);
	}

}