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

public class EnergyCellMobEntity extends AbstractHurtingProjectile implements GeoEntity {

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private float directHitDamage = 3F;
    private LivingEntity shooter;

    public EnergyCellMobEntity(EntityType<? extends EnergyCellMobEntity> type, Level level) {
        super(type, level);
    }

    public EnergyCellMobEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getEnergyCellMobEntity(), shooter, accelX, accelY,
                accelZ, worldIn);
        this.shooter = shooter;
        walkDist = 3.0F;
        this.directHitDamage = directHitDamage;
    }

    public EnergyCellMobEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getEnergyCellMobEntity(), x, y, z, accelX, accelY,
                accelZ, worldIn);
    }

    public void setDirectHitDamage(float directHitDamage) {
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
        this.playSound(Services.SOUNDS_HELPER.getPLASMA_HIT(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
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
                if (!(entity2 instanceof DemonEntity))
                    doEnchantDamageEffects(livingEntity, entity);
                remove(RemovalReason.DISCARDED);
            }
        }
        this.playSound(Services.SOUNDS_HELPER.getPLASMA_HIT(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
    }

    protected void explode() {
        level().getEntities(this, new AABB(blockPosition().above()).inflate(8)).forEach(e -> doDamage(this, e));
    }

    private void doDamage(Entity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            target.hurt(damageSources().indirectMagic(this, target), directHitDamage);
        }
    }

    public LivingEntity getShooter() {
        return shooter;
    }

    public void setShooter(LivingEntity shooter) {
        this.shooter = shooter;
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= 80)
            remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}