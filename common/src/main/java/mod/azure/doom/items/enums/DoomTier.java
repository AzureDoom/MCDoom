package mod.azure.doom.items.enums;

import mod.azure.doom.platform.Services;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum DoomTier implements Tier {
    DOOM(18, 1561, 16.0F, 3.0F, 30, () -> Ingredient.of(Services.ITEMS_HELPER.getArgentEngery())), DOOM_HIGHTEIR(6, 0, 16.0F, -1.9F, 30, () -> Ingredient.of(Services.ITEMS_HELPER.getArgentBlock()));

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