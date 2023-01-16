package mod.azure.doom.util.enums;

import java.util.function.Supplier;

import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum DoomTier implements Tier {
	DOOM(18, 1561, 16.0F, 3.0F, 30, () -> {
		return Ingredient.of(DoomItems.ARGENT_ENERGY);
	}), DOOM_HIGHTEIR(6, 0, 16.0F, -1.9F, 30, () -> {
		return Ingredient.of(DoomBlocks.ARGENT_BLOCK);
	}), CHAINSAW(6, 5, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.GAS_BARREL);
	}), PISTOL(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.BULLETS);
	}), BALLISTA(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.ARGENT_BOLT);
	}), BFG(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.BFG_CELL);
	}), CHAINGUN(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.CHAINGUN_BULLETS);
	}), PLASMA(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.ENERGY_CELLS);
	}), ROCKET(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.ROCKET);
	}), SHOTGUN(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.SHOTGUN_SHELLS);
	}), UNMAYKR(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.UNMAKRY_BOLT);
	});

	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final LazyLoadedValue<Ingredient> repairIngredient;

	private DoomTier(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability,
			Supplier<Ingredient> repairIngredient) {

		this.miningLevel = miningLevel;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = new LazyLoadedValue<Ingredient>(repairIngredient);
	}

	@Override
	public int getUses() {
		return this.itemDurability;
	}

	@Override
	public float getSpeed() {
		return this.miningSpeed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.miningLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return (Ingredient) this.repairIngredient.get();
	}

}