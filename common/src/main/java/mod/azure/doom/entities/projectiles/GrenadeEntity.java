package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.CacodemonEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
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

public class GrenadeEntity extends AbstractArrow implements GeoEntity {

    private static final EntityDataAccessor<Boolean> SPINNING = SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public SoundEvent hitSound = getDefaultHitGroundSoundEvent();
    protected String type;
    private LivingEntity shooter;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level world) {
        super(entityType, world);
        pickup = Pickup.DISALLOWED;
    }

    public GrenadeEntity(Level world, LivingEntity owner) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getGranadeEntity(), owner, world);
        shooter = owner;
    }

    protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        setOwner(owner);
        shooter = owner;
        if (owner instanceof Player) pickup = Pickup.DISALLOWED;
    }

    public GrenadeEntity(Level world, LivingEntity user, boolean spinning) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getGranadeEntity(), user, world);
        entityData.set(SPINNING, spinning);
        shooter = user;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (inGroundTime == 0) return event.setAndContinue(RawAnimation.begin().thenLoop("spin"));
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SPINNING, false);
    }

    public boolean isSpinning() {
        return entityData.get(SPINNING);
    }

    public void setSpinning(boolean spin) {
        entityData.set(SPINNING, spin);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public void remove(RemovalReason reason) {
        final var areaeffectcloudentity = new AreaEffectCloud(level(), this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.FLAME);
        areaeffectcloudentity.setRadius(6);
        areaeffectcloudentity.setDuration(1);
        areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
        level().addFreshEntity(areaeffectcloudentity);
        explode();
        level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.5F);
        super.remove(reason);
    }

    @Override
    protected void tickDespawn() {
        if (tickCount >= 40) this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("State", isSpinning());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setSpinning(compound.getBoolean("State"));
    }

    @Override
    public void tick() {
        super.tick();
        if (getDeltaMovement().x == 0) setSpinning(false);
        if (!onGround()) setSpinning(true);
        if (tickCount >= 46 && !this.level().isClientSide()) this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        hitSound = soundIn;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getBEEP();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!level().isClientSide() && tickCount >= 46) remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        final var entity = entityHitResult.getEntity();
        if (!level().isClientSide()) {
            if (entity instanceof CacodemonEntity) {
                entity.hurt(damageSources().playerAttack((Player) shooter), ((LivingEntity) entity).getMaxHealth());
                remove(RemovalReason.DISCARDED);
            } else {
                super.onHitEntity(entityHitResult);
                explode();
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    protected void explode() {
        level().getEntities(this, new AABB(blockPosition().above()).inflate(8)).forEach(e -> doDamage(this, e));
    }

    private void doDamage(Entity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            if (this.isOnFire())
                target.setSecondsOnFire(50);
            target.hurt(damageSources().indirectMagic(this, target), MCDoom.config.grenade_damage);
            target.setDeltaMovement(target.getDeltaMovement().add(1.0, 0.6, 1.0));
        }
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}