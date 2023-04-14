package mod.azure.doom.util.enums;

import java.util.function.Supplier;

import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum DoomTier implements Tier {
	CHAINSAW(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.GAS_BARREL.get())), PISTOL(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.BULLETS.get())), BALLISTA(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.ARGENT_BOLT.get())), BFG(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.BFG_CELL.get())), CHAINGUN(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.CHAINGUN_BULLETS.get())),
	PLASMA(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.ENERGY_CELLS.get())), ROCKET(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.ROCKET.get())), SHOTGUN(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.SHOTGUN_SHELLS.get())), UNMAYKR(6, 600, 16.0F, 0.0F, 30, () -> Ingredient.of(DoomItems.UNMAKRY_BOLT.get()));

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyLoadedValue<Ingredient> repairMaterial;

	private DoomTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		harvestLevel = harvestLevelIn;
		maxUses = maxUsesIn;
		efficiency = efficiencyIn;
		attackDamage = attackDamageIn;
		enchantability = enchantabilityIn;
		repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
	}

	@Override
	public int getUses() {
		return maxUses;
	}

	@Override
	public float getSpeed() {
		return efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return attackDamage;
	}

	@Override
	public int getLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairMaterial.get();
	}

}