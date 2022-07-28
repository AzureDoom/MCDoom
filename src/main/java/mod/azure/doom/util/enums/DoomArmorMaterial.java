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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("deprecation")
public enum DoomArmorMaterial implements ArmorMaterial {
	DOOM_ARMOR("doomweapon:doom_armor", 500,
			new int[] { DoomConfig.SERVER.doom_armor_head_stat.get(),
					DoomConfig.SERVER.doom_armor_chestplate_stat.get(),
					DoomConfig.SERVER.doom_armor_leggings_stat.get(), DoomConfig.SERVER.doom_armor_boots_stat.get() },
			40, SoundEvents.ARMOR_EQUIP_GENERIC, 24.0F, 4.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			});

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 5, 8, 10, 5 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyLoadedValue<Ingredient> repairMaterial;

	private DoomArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn,
			int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, float knockbackResistance,
			Supplier<Ingredient> repairMaterialSupplier) {
		this.name = nameIn;
		this.maxDamageFactor = maxDamageFactorIn;
		this.damageReductionAmountArray = damageReductionAmountsIn;
		this.enchantability = enchantabilityIn;
		this.soundEvent = equipSoundIn;
		this.toughness = toughnessIn;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterialSupplier);
	}

	public int getDurabilityForSlot(EquipmentSlot slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	public int getDefenseForSlot(EquipmentSlot slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	public int getEnchantmentValue() {
		return this.enchantability;
	}

	public SoundEvent getEquipSound() {
		return this.soundEvent;
	}

	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}