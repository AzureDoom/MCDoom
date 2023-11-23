package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.helper.CommonUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class RocketEntity extends AbstractArrow implements GeoEntity {
    private LivingEntity shooter;
    private float projectiledamage;
    private int idleTicks = 0;
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public SoundEvent hitSound = getDefaultHitGroundSoundEvent();

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
        super(entityType, world);
        pickup = Pickup.DISALLOWED;
    }

    public RocketEntity(Level world, LivingEntity owner) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getRocketEntity(), owner, world);
        shooter = owner;
    }

    public RocketEntity(Level world, LivingEntity owner, float damage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getRocketEntity(), owner, world);
        shooter = owner;
        projectiledamage = damage;
    }

    protected RocketEntity(EntityType<? extends RocketEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected RocketEntity(EntityType<? extends RocketEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        setOwner(owner);
        if (owner instanceof Player) pickup = Pickup.DISALLOWED;
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
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!(living instanceof Player) && !(living instanceof IconofsinEntity)) {
            living.setDeltaMovement(0, 0, 0);
            living.invulnerableTime = 0;
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected void tickDespawn() {
        if (tickCount >= 40) remove(RemovalReason.KILLED);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    public void tick() {
        final var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        if (this.tickCount >= 80) remove(RemovalReason.DISCARDED);
        final var isInsideWaterBlock = level().isWaterAt(blockPosition());
        CommonUtils.spawnLightSource(this, isInsideWaterBlock);
        if (level().isClientSide())
            level().addParticle(ParticleTypes.SMOKE, true, this.getX() + random.nextDouble() * getBbWidth() * 0.5D, this.getY(0.5), this.getZ() + random.nextDouble() * getBbWidth() * 0.5D, 0, 0, 0);
    }

    @Override
    public boolean isNoGravity() {
        return !isInWater();
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        hitSound = soundIn;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getROCKET_HIT();
    }

    @Override
    public void remove(RemovalReason reason) {
        final var areaeffectcloudentity = new AreaEffectCloud(level(), this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.LAVA);
        areaeffectcloudentity.setRadius(6);
        areaeffectcloudentity.setDuration(1);
        areaeffectcloudentity.absMoveTo(this.getX(), this.getY(), this.getZ());
        level().addFreshEntity(areaeffectcloudentity);
        level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.5F);
        super.remove(reason);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!level().isClientSide()) {
            doDamage();
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!level().isClientSide()) {
            doDamage();
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public ItemStack getPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    public void doDamage() {
        level().getEntities(this, new AABB(blockPosition().above()).inflate(4)).forEach(e -> {
            if (e instanceof LivingEntity) e.hurt(damageSources().playerAttack((Player) shooter), projectiledamage);
            level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 0.0F, Level.ExplosionInteraction.NONE);
        });

    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}