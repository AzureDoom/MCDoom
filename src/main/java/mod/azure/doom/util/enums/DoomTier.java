package mod.azure.doom.util.enums;

import java.util.function.Supplier;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

public enum DoomTier implements ToolMaterial {
	DOOM(18, 1561, 16.0F, 3.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.ARGENT_ENERGY);
	}), DOOM_HIGHTEIR(6, DoomMod.config.weapons.crucible_marauder_max_damage, 16.0F, 3.0F, 30, () -> {
		return Ingredient.ofItems(DoomBlocks.ARGENT_BLOCK);
	}), CHAINSAW(6, 5, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.GAS_BARREL);
	}), PISTOL(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.BULLETS);
	}), BALLISTA(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.ARGENT_BOLT);
	}), BFG(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.BFG_CELL);
	}), CHAINGUN(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.CHAINGUN_BULLETS);
	}), PLASMA(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.ENERGY_CELLS);
	}), ROCKET(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.ROCKET);
	}), SHOTGUN(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.SHOTGUN_SHELLS);
	}), UNMAYKR(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.ofItems(DoomItems.UNMAKRY_BOLT);
	});

	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Lazy<Ingredient> repairIngredient;

	private DoomTier(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability,
			Supplier<Ingredient> repairIngredient) {

		this.miningLevel = miningLevel;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = new Lazy<Ingredient>(repairIngredient);
	}

	public int getDurability() {
		return this.itemDurability;
	}

	public float getMiningSpeedMultiplier() {
		return this.miningSpeed;
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public int getMiningLevel() {
		return this.miningLevel;
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public Ingredient getRepairIngredient() {
		return (Ingredient) this.repairIngredient.get();
	}

}