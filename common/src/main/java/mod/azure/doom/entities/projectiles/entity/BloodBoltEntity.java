package mod.azure.doom.entities.projectiles.entity;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class BloodBoltEntity extends AbstractHurtingProjectile implements GeoEntity {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private float directHitDamage = 2;

    public BloodBoltEntity(EntityType<BloodBoltEntity> entity, Level level) {
        super(entity, level);
    }

    public BloodBoltEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getBloodBoltEntity(), shooter, accelX, accelY, accelZ,
                worldIn);
        this.directHitDamage = directHitDamage;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return !isInWater();
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.PORTAL;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide()) {
            explode();
            remove(RemovalReason.DISCARDED);
        }
        this.playSound(Services.SOUNDS_HELPER.getUNMAKYR_FIRE(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!level().isClientSide()) {
            final var entity = entityHitResult.getEntity();
            final var entity2 = getOwner();
            if (!(entity instanceof DemonEntity) && entity instanceof LivingEntity)
                entity.hurt(damageSources().mobAttack((LivingEntity) entity2), directHitDamage);
            if (entity2 instanceof LivingEntity livingEntity) {
                if (!(entity instanceof DemonEntity)) doEnchantDamageEffects(livingEntity, entity);
                remove(RemovalReason.DISCARDED);
            }
        }
        this.playSound(Services.SOUNDS_HELPER.getUNMAKYR_FIRE(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    protected void explode() {
        level().getEntities(this, new AABB(blockPosition().above()).inflate(8)).forEach(this::doDamage);
    }

    private void doDamage(Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            target.hurt(damageSources().indirectMagic(this, target), directHitDamage);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= 80) remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}