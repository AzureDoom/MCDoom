package mod.azure.doom.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

/**
 * 
 * @credit to https://gitlab.com/modding-legacy/
 *
 */
public class LeapingDoomEnchantment extends Enchantment {

	public LeapingDoomEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
		super(rarityIn, EnchantmentTarget.ARMOR_FEET, slots);
	}

	@Override
	public int getMaxPower(int level) {
		return 1;
	}

	@Override
	public int getMinPower(int level) {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean isTreasure() {
		return true;
	}

	@Override
	public boolean isAvailableForEnchantedBookOffer() {
		return true;
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem ? true : false;
	}

	public boolean isAvailableForRandomSelection() {
		return true;
	}
}