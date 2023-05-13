package mod.azure.doom.util.enums;

import java.util.function.Supplier;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum DAMat implements ArmorMaterial {
	DOOM_ARMOR("doomweapon:doom_armor", 500, new int[] { DoomConfig.SERVER.doom_armor_boots_stat.get(), DoomConfig.SERVER.doom_armor_leggings_stat.get(), DoomConfig.SERVER.doom_armor_chestplate_stat.get(), DoomConfig.SERVER.doom_armor_head_stat.get() }, 40, SoundEvents.ARMOR_EQUIP_GENERIC, DoomConfig.SERVER.doom_armor_toughness.get().floatValue(), DoomConfig.SERVER.doom_armor_knockbackResistance.get().floatValue(), () -> Ingredient.of(DoomItems.ARGENT_ENERGY.get()));

	private static final int[] BASE_DURABILITY = { DoomConfig.SERVER.doom_armor_boots_stat.get(), DoomConfig.SERVER.doom_armor_leggings_stat.get(), DoomConfig.SERVER.doom_armor_chestplate_stat.get(), DoomConfig.SERVER.doom_armor_head_stat.get() };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairIngredientSupplier;

	private DAMat(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
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
	public SoundEvent getEquipSound() {
		return equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairIngredientSupplier.get();
	}

	@Override
	public String getName() {
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
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getIndex()] * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return this.protectionAmounts[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}
}