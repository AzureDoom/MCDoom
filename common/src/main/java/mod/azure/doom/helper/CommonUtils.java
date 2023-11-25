package mod.azure.doom.helper;

import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.*;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class CommonUtils {

    private CommonUtils() {
    }

    /**
     * Hitscan between the player and the target. Useful for doing damage
     * TODO: Fix why it doesn't work if going about shoulder level on zombie sized mobs
     *
     * @param livingEntity The Shooter Entity.
     * @param range        The block distance it can fire.
     * @param ticks        The amount of ticks to take, usually will be 1.0f
     * @return returns a EntityHitResult
     */
    public static EntityHitResult hitscanTrace(LivingEntity livingEntity, double range, float ticks) {
        var look = livingEntity.getViewVector(ticks);
        var start = livingEntity.getEyePosition(ticks);
        var end = new Vec3(livingEntity.getX() + look.x * range, livingEntity.getEyeY() + look.y * range, livingEntity.getZ() + look.z * range);
        var traceDistance = livingEntity.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, livingEntity)).getLocation().distanceToSqr(end);
        for (var possible : livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(3.0D, 3.0D, 3.0D), (entity -> !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity))) {
            var clip = possible.getBoundingBox().inflate(0.3D).clip(start, end);
            if (clip.isPresent() && start.distanceToSqr(clip.get()) < traceDistance)
                return ProjectileUtil.getEntityHitResult(livingEntity.level(), livingEntity, start, end, livingEntity.getBoundingBox().expandTowards(look.scale(traceDistance)).inflate(3.0D, 3.0D, 3.0D), target -> !target.isSpectator() && livingEntity.isAttackable() && livingEntity.hasLineOfSight(target));
        }
        return null;
    }

    /**
     * Removes matching item from offhand first then checks inventory for item
     *
     * @param ammo         Item you want to be used as ammo
     * @param playerEntity Player whose inventory is being checked.
     */
    public static void removeAmmo(Item ammo, Player playerEntity) {
        if (playerEntity.getItemInHand(playerEntity.getUsedItemHand()).getItem() instanceof DoomBaseItem gunItem && !playerEntity.isCreative()) { // Creative mode reloading breaks things
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
            entity.level().setBlockAndUpdate(lightBlockPos, mod.azure.azurelib.platform.Services.PLATFORM.getTickingLightBlock().defaultBlockState());
        } else if (CommonUtils.checkDistance(lightBlockPos, entity.blockPosition()) && entity.level().getBlockEntity(lightBlockPos) instanceof TickingLightEntity tickingLightEntity) {
            tickingLightEntity.refresh(isInWaterBlock ? 20 : 0);
        }
    }

    private static boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB) {
        return Math.abs(blockPosA.getX() - blockPosB.getX()) <= 2 && Math.abs(blockPosA.getY() - blockPosB.getY()) <= 2 && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= 2;
    }

    private static BlockPos findFreeSpace(Level world, BlockPos blockPos) {
        if (blockPos == null) return null;

        int[] offsets = new int[2 * 2 + 1];
        offsets[0] = 0;
        for (int i = 2; i <= 2 * 2; i += 2) {
            offsets[i - 1] = i / 2;
            offsets[i] = -i / 2;
        }
        for (int x : offsets)
            for (int y : offsets)
                for (int z : offsets) {
                    BlockPos offsetPos = blockPos.offset(x, y, z);
                    BlockState state = world.getBlockState(offsetPos);
                    if (state.isAir() || state.getBlock().equals(mod.azure.azurelib.platform.Services.PLATFORM.getTickingLightBlock()))
                        return offsetPos;
                }
        return null;
    }

    public static BulletEntity createBullet(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new BulletEntity(worldIn, shooter, MCDoom.config.bullet_damage + enchantlevel * 2.0F);
    }

    public static BulletEntity createCBullet(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new BulletEntity(worldIn, shooter, MCDoom.config.chaingun_bullet_damage + enchantlevel * 2.0F);
    }

    public static UnmaykrBoltEntity createUnmakyer(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new UnmaykrBoltEntity(worldIn, shooter, MCDoom.config.unmaykr_damage + enchantlevel * 2.0F);
    }

    public static ShotgunShellEntity createShell(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new ShotgunShellEntity(worldIn, shooter, MCDoom.config.shotgun_damage + enchantlevel * 2.0F);
    }

    public static RocketEntity createRocket(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new RocketEntity(worldIn, shooter, MCDoom.config.rocket_damage + enchantlevel * 2.0F);
    }

    public static EnergyCellEntity createEnergyCell(Level worldIn, ItemStack stack, LivingEntity shooter) {
        final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        return new EnergyCellEntity(worldIn, shooter, MCDoom.config.energycell_damage + enchantlevel * 2.0F);
    }

    public static BFGEntity createBFG(Level worldIn, ItemStack stack, LivingEntity shooter) {
        return new BFGEntity(worldIn, shooter);
    }

    public static ArgentBoltEntity createABolt(Level worldIn, ItemStack stack, LivingEntity shooter) {
        return new ArgentBoltEntity(worldIn, shooter);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    public static boolean nonCentered(ItemStack stack) {
        return MCDoom.config.enable_noncenter;
    }

    public static void setOnFire(Entity projectile) {
        if (projectile.isOnFire())
            projectile.level().getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(2)).forEach(e -> {
                if (e.isAlive())
                    e.setRemainingFireTicks(90);
            });
    }
}
