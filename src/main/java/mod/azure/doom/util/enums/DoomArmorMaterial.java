package mod.azure.doom.util.enums;

import java.util.function.Supplier;

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
	DOOM_ARMOR("doomweapon:doom_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F,
			() -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	PRAETOR_DOOM_ARMOR("doomweapon:praetor_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	CLASSIC_DOOM_ARMOR("doomweapon:classic_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	MIDNIGHT_DOOM_ARMOR("doomweapon:midnight_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	ASTRO_DOOM_ARMOR("doomweapon:astro_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F,
			() -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	DEMONCIDE_DOOM_ARMOR("doomweapon:demoncide_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	DEMONIC_DOOM_ARMOR("doomweapon:demonic_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	HOTROD_DOOM_ARMOR("doomweapon:hotrod_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	SENTINEL_DOOM_ARMOR("doomweapon:sentinel_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	EMBER_DOOM_ARMOR("doomweapon:ember_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F,
			() -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	ZOMBIE_DOOM_ARMOR("doomweapon:zombie_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	DOOMICORN_DOOM_ARMOR("doomweapon:doomicorn_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	PURPLEPONY_DOOM_ARMOR("doomweapon:purplepony_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	NIGHTMARE_DOOM_ARMOR("doomweapon:nightmare_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	PHOBOS_DOOM_ARMOR("doomweapon:phobos_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	CLASSIC_RED_ARMOR("doomweapon:classic_red_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	CLASSIC_INDIGO_ARMOR("doomweapon:classic_indigo_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	CLASSIC_BRONZE_ARMOR("doomweapon:classic_bronze_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	GOLD_ARMOR("doomweapon:gold_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F,
			() -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	TWENTY_FIVE_ARMOR("doomweapon:twenty_five_armor", 50, new int[] { 5, 8, 10, 5 }, 30,
			SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	REDNECK1_ARMOR("doomweapon:redneck1_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	REDNECK2_ARMOR("doomweapon:redneck2_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	REDNECK3_ARMOR("doomweapon:redneck3_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	BRONZE_DOOM_ARMOR("doomweapon:bronze_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	PAINTER_DOOM_ARMOR("doomweapon:painter_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	CULTIST_DOOM_ARMOR("doomweapon:cultist_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	MAYKR_DOOM_ARMOR("doomweapon:maykr_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 8.0F,
			() -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			}),
	CRIMSON_DOOM_ARMOR("doomweapon:crimson_armor", 50, new int[] { 5, 8, 10, 5 }, 30, SoundEvents.ARMOR_EQUIP_GENERIC,
			8.0F, () -> {
				return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
			});

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 5, 8, 10, 5 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyLoadedValue<Ingredient> repairMaterial;

	private DoomArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn,
			int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn,
			Supplier<Ingredient> repairMaterialSupplier) {
		this.name = nameIn;
		this.maxDamageFactor = maxDamageFactorIn;
		this.damageReductionAmountArray = damageReductionAmountsIn;
		this.enchantability = enchantabilityIn;
		this.soundEvent = equipSoundIn;
		this.toughness = toughnessIn;
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
		return 0;
	}
}