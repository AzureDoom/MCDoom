package mod.azure.doom.entities.projectiles.entity;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class DroneBoltEntity extends AbstractHurtingProjectile {

    private float directHitDamage = 2;

    public DroneBoltEntity(EntityType<DroneBoltEntity> entity, Level level) {
        super(entity, level);
    }

    public DroneBoltEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getDroneBoltEntity(), shooter, accelX, accelY, accelZ, worldIn);
        this.directHitDamage = directHitDamage;
    }

    public DroneBoltEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getDroneBoltEntity(), x, y, z, accelX, accelY, accelZ, worldIn);
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
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide()) {
            explode();
            remove(RemovalReason.DISCARDED);
        }
        this.playSound(Services.SOUNDS_HELPER.getROCKET_HIT(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    protected void explode() {
        level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false, MCDoom.config.enable_block_breaking ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
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
        this.playSound(Services.SOUNDS_HELPER.getUNMAKYR_FIRE(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
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