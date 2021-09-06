package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.enchantments.LeapingDoomEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoomEnchantments {

	public static final Enchantment LEAPING_DOOM = new LeapingDoomEnchantment(Rarity.RARE, EquipmentSlot.FEET);

	public static void init() {
		Registry.register(Registry.ENCHANTMENT, new Identifier(DoomMod.MODID, "leaping_doom"), LEAPING_DOOM);
	}
}