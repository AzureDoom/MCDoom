package mod.azure.doom.helper;

import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.BFGEntity;
import mod.azure.doom.entities.projectiles.BulletEntity;
import mod.azure.doom.entities.projectiles.RocketEntity;
import mod.azure.doom.items.weapons.BaseSwordItem;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class CommonUtils {

    private CommonUtils() {
    }

    /**
     * Removes matching item from offhand first then checks inventory for item
     *
     * @param ammo         Item you want to be used as ammo
     * @param playerEntity Player whose inventory is being checked.
     */
    public static void removeAmmo(Item ammo, Player playerEntity) {
        if ((playerEntity.getItemInHand(
                playerEntity.getUsedItemHand()).getItem() instanceof DoomBaseItem || playerEntity.getItemInHand(
                playerEntity.getUsedItemHand()).getItem() instanceof BaseSwordItem) && !playerEntity.isCreative()) { // Creative mode reloading breaks things
            for (var item : playerEntity.getInventory().offhand) {
                if (item.getItem() == ammo) {
                    item.shrink(1);
                    break;
                }
                for (var item1 : playerEntity.getInventory().items) {
                    if (item1.getItem() == ammo) {
                        item1.shrink(1);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Call wherever you are firing weapon to place the half tick light-block, making sure do so only on the server.
     *
     * @param entity         Usually the player or mob that is using the weapon
     * @param isInWaterBlock Checks if it's in a water block to refresh faster.
     */
    public static void spawnLightSource(Entity entity, boolean isInWaterBlock) {
        BlockPos lightBlockPos = null;
        if (lightBlockPos == null) {
            lightBlockPos = CommonUtils.findFreeSpace(entity.level(), entity.blockPosition());
            if (lightBlockPos == null) return;
            entity.level().setBlockAndUpdate(lightBlockPos,
                    mod.azure.azurelib.platform.Services.PLATFORM.getTickingLightBlock().defaultBlockState());
        } else if (CommonUtils.checkDistance(lightBlockPos, entity.blockPosition()) && entity.level().getBlockEntity(
                lightBlockPos) instanceof TickingLightEntity tickingLightEntity) {
            tickingLightEntity.refresh(isInWaterBlock ? 20 : 0);
        }
    }

    private static boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB) {
        return Math.abs(blockPosA.getX() - blockPosB.getX()) <= 2 && Math.abs(
                blockPosA.getY() - blockPosB.getY()) <= 2 && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= 2;
    }

    private static BlockPos findFreeSpace(Level world, BlockPos blockPos) {
        if (blockPos == null) return null;

        var offsets = new int[2 * 2 + 1];
        offsets[0] = 0;
        for (var i = 2; i <= 2 * 2; i += 2) {
            offsets[i - 1] = i / 2;
            offsets[i] = -i / 2;
        }
        for (var x : offsets)
            for (var y : offsets)
                for (var z : offsets) {
                    var offsetPos = blockPos.offset(x, y, z);
                    var state = world.getBlockState(offsetPos);
                    if (state.isAir() || state.getBlock().equals(
                            mod.azure.azurelib.platform.Services.PLATFORM.getTickingLightBlock()))
                        return offsetPos;
                }
        return null;
    }

    public static BulletEntity createBullet(Level worldIn, ItemStack stack, LivingEntity shooter, float damage) {
        final var enchantment = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new BulletEntity(worldIn, shooter, damage + enchantment * 2.0F);
    }

    public static RocketEntity createRocket(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new RocketEntity(worldIn, shooter, MCDoom.config.rocket_damage + enchantlevel * 2.0F);
    }

    public static BFGEntity createBFG(Level worldIn, LivingEntity shooter) {
        return new BFGEntity(worldIn, shooter);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    public static boolean nonCentered() {
        return MCDoom.config.enable_noncenter;
    }

    public static void setOnFire(Projectile projectile) {
        if (projectile.isOnFire())
            projectile.level().getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(2)).forEach(
                    e -> {
                        if (e.isAlive() && !(e instanceof Player)) e.setRemainingFireTicks(90);
                    });
    }
}
