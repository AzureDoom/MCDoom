package mod.azure.doom.entities.projectiles.entity;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.helper.CommonUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class FireProjectile extends AbstractHurtingProjectile {

    private float directHitDamage = 2;
    private int idleTicks = 0;

    public FireProjectile(EntityType<FireProjectile> entitytype, Level world) {
        super(entitytype, world);
    }

    public FireProjectile(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getFireEntity(), shooter, accelX, accelY, accelZ,
                worldIn);
        this.directHitDamage = directHitDamage;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    @Override
    public void tick() {
        final var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        if (this.tickCount >= 40) remove(RemovalReason.DISCARDED);
        final var isInsideWaterBlock = level().isWaterAt(blockPosition());
        CommonUtils.spawnLightSource(this, isInsideWaterBlock);
        if (level().isClientSide()) {
            level().addParticle(ParticleTypes.FLAME, true,
                    this.getX() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, this.getY(),
                    this.getZ() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, 0, 0, 0);
            level().addParticle(ParticleTypes.SMOKE, true,
                    this.getX() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, this.getY(),
                    this.getZ() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, 0, 0, 0);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        final var blockpos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        if (!level().isClientSide) {
            return;
        }
        final var entity = getOwner();
        if (!(entity instanceof Mob) || level().getGameRules().getBoolean(
                GameRules.RULE_MOBGRIEFING) && (level().isEmptyBlock(blockpos))) {
            level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(level(), blockpos));
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!level().isClientSide) {
            final var entity = entityHitResult.getEntity();
            final var entity1 = getOwner();
            remove(RemovalReason.KILLED);
            if (entity1 instanceof LivingEntity livingEntity && (!(entity instanceof DemonEntity))) {
                entity.hurt(damageSources().lava(), directHitDamage);
                entity.setSecondsOnFire(15);
                doEnchantDamageEffects(livingEntity, entity);
            }
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}
