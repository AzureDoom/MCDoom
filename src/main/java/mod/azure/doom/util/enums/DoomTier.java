package mod.azure.doom.util.enums;

import java.util.function.Supplier;

import mod.azure.doom.util.config.Config;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;

@SuppressWarnings("deprecation")
public enum DoomTier implements Tier {
	DOOM(18, 1561, 16.0F, 3.0F, 30, () -> {
		return Ingredient.of(DoomItems.ARGENT_ENERGY.get());
	}), DOOM_HIGHTEIR(6, Config.SERVER.CRUCIBLE_MARAUDER_MAX_DAMAGE, 16.0F, 3.0F, 30, () -> {
		return Ingredient.of(DoomItems.ARGENT_BLOCK.get());
	}), CHAINSAW(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.GAS_BARREL.get());
	}), PISTOL(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.BULLETS.get());
	}), BALLISTA(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.ARGENT_BOLT.get());
	}), BFG(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.BFG_CELL.get());
	}), CHAINGUN(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.CHAINGUN_BULLETS.get());
	}), PLASMA(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.ENERGY_CELLS.get());
	}), ROCKET(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.ROCKET.get());
	}), SHOTGUN(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.SHOTGUN_SHELLS.get());
	}), UNMAYKR(6, 600, 16.0F, 0.0F, 30, () -> {
		return Ingredient.of(DoomItems.UNMAKRY_BOLT.get());
	});

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyLoadedValue<Ingredient> repairMaterial;

	private DoomTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn,
			Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
	}

	public int getUses() {
		return this.maxUses;
	}

	public float getSpeed() {
		return this.efficiency;
	}

	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	public int getLevel() {
		return this.harvestLevel;
	}

	public int getEnchantmentValue() {
		return this.enchantability;
	}

	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

}