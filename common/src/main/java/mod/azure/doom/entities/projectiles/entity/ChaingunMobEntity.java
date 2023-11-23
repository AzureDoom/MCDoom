package mod.azure.doom.entities.projectiles.entity;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ChaingunMobEntity extends AbstractHurtingProjectile {
    private float directHitDamage = 3F;

    public ChaingunMobEntity(EntityType<? extends ChaingunMobEntity> p_i50160_1_, Level p_i50160_2_) {
        super(p_i50160_1_, p_i50160_2_);
    }

    public ChaingunMobEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getChaingunBulletMobEntity(), shooter, accelX, accelY, accelZ, worldIn);
        this.directHitDamage = directHitDamage;
    }

    public ChaingunMobEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getChaingunBulletMobEntity(), x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= 80) remove(RemovalReason.DISCARDED);
        if (level().isClientSide())
            level().addParticle(ParticleTypes.SMOKE, true, this.getX() + random.nextDouble() * getBbWidth() * 0.5D, this.getY(), this.getZ() + random.nextDouble() * getBbWidth() * 0.5D, 0, 0, 0);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    public void setDirectHitDamage(float directHitDamage) {
        this.directHitDamage = directHitDamage;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return !isInWater();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!level().isClientSide()) {
            final var entity = entityHitResult.getEntity();
            final var entity2 = getOwner();
            if (!(entity instanceof DemonEntity))
                entity.hurt(damageSources().mobAttack((LivingEntity) entity2), directHitDamage);
            if (entity2 instanceof LivingEntity livingEntity) {
                if (!(entity instanceof DemonEntity)) doEnchantDamageEffects(livingEntity, entity);
                remove(RemovalReason.DISCARDED);
            }
        }
        this.playSound(Services.SOUNDS_HELPER.getCHAINGUN_SHOOT(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}