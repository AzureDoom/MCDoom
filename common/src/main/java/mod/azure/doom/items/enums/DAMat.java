package mod.azure.doom.items.enums;

import mod.azure.doom.MCDoom;
import mod.azure.doom.platform.Services;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum DAMat implements ArmorMaterial {
    DOOM_ARMOR("doomweapon:doom_armor", 500,
            new int[]{MCDoom.config.doom_armor_boots_stat, MCDoom.config.doom_armor_leggings_stat, MCDoom.config.doom_armor_chestplate_stat, MCDoom.config.doom_armor_head_stat},
            40, SoundEvents.ARMOR_EQUIP_GENERIC, MCDoom.config.doom_armor_toughness,
            MCDoom.config.doom_armor_knockbackResistance, () -> Ingredient.of(Services.ITEMS_HELPER.getArgentEngery()));

    private static final int[] BASE_DURABILITY = {MCDoom.config.doom_armor_boots_stat, MCDoom.config.doom_armor_leggings_stat, MCDoom.config.doom_armor_chestplate_stat, MCDoom.config.doom_armor_head_stat};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredientSupplier;

    DAMat(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        repairIngredientSupplier = new LazyLoadedValue<>(supplier);
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredientSupplier.get();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    @Override
    public int getDurabilityForType(Type slot) {
        return BASE_DURABILITY[slot.getSlot().getIndex()] * durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(Type slot) {
        return protectionAmounts[slot.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }
}