package mod.azure.doom.enchantments;

import mod.azure.doom.items.enums.GunTypeEnum;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StickyBombEnchantment extends Enchantment {

    public StickyBombEnchantment(EquipmentSlot... slotTypes) {
        super(Rarity.RARE, EnchantmentCategory.BREAKABLE, slotTypes);
    }

    @Override
    public int getMaxCost(int level) {
        return 1;
    }

    @Override
    public int getMinCost(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return (stack.getItem() instanceof DoomBaseItem gunItem && gunItem.getGunTypeEnum() == GunTypeEnum.DSHOTGUN) ? true : false;
    }

    public boolean isDiscoverable() {
        return true;
    }
}
