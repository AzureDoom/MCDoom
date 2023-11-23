package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.helper.CommonUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ArgentBoltEntity extends AbstractArrow {

    protected int timeInAir;
    protected boolean inAir;
    private int ticksInAir;
    public static final EntityDataAccessor<Boolean> PARTICLE = SynchedEntityData.defineId(ArgentBoltEntity.class, EntityDataSerializers.BOOLEAN);
    private LivingEntity shooter;
    private int idleTicks = 0;
    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    public ArgentBoltEntity(EntityType<? extends ArgentBoltEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public ArgentBoltEntity(Level world, LivingEntity owner) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getArgentBoltEntity(), owner, world);
        this.shooter = owner;
    }

    protected ArgentBoltEntity(EntityType<? extends ArgentBoltEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected ArgentBoltEntity(EntityType<? extends ArgentBoltEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        this.shooter = owner;
        if (owner instanceof Player) this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PARTICLE, false);
    }

    public boolean useParticle() {
        return this.entityData.get(PARTICLE);
    }

    public void setParticle(boolean spin) {
        this.entityData.set(PARTICLE, spin);
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
        ++this.ticksInAir;
        if (this.tickCount >= 40) this.remove(RemovalReason.KILLED);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.ticksInAir = 0;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("life", (short) this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.ticksInAir = compound.getShort("life");
    }

    @Override
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        ++this.ticksInAir;
        if (this.ticksInAir >= 80) this.remove(RemovalReason.DISCARDED);
        var isInsideWaterBlock = level().isWaterAt(blockPosition());
        CommonUtils.spawnLightSource(this, isInsideWaterBlock);
        if (this.level().isClientSide())
            this.level().addParticle(this.useParticle() ? ParticleTypes.ANGRY_VILLAGER : ParticleTypes.FLASH, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
    }

    @Override
    public boolean isNoGravity() {
        return !this.isInWater();
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (this.useParticle()) {
            AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.LAVA);
            areaeffectcloudentity.setRadius(6);
            areaeffectcloudentity.setDuration(1);
            areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(areaeffectcloudentity);
            level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.5F);
        }
        super.remove(reason);
    }

    protected void explode() {
        final AABB aabb = new AABB(this.blockPosition().above()).inflate(2D, 2D, 2D);
        this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
            if (e instanceof LivingEntity) {
                e.hurt(damageSources().playerAttack((Player) this.shooter), MCDoom.config.argent_bolt_damage);
            }
        });
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !(entityHitResult).getEntity().is(entity) && !this.level().isClientSide)
            this.remove(RemovalReason.KILLED);
        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = damageSources().arrow(this, this);
        } else {
            damagesource = damageSources().arrow(this, entity1);
            if (entity1 instanceof LivingEntity livingEntity) {
                livingEntity.setLastHurtMob(entity);
            }
        }
        if (entity.hurt(damagesource, MCDoom.config.argent_bolt_damage)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!this.level().isClientSide && entity1 instanceof LivingEntity livingEntity1) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity, entity1);
                    EnchantmentHelper.doPostDamageEffects(livingEntity1, livingEntity);
                    this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F, Level.ExplosionInteraction.NONE);
                    this.remove(RemovalReason.KILLED);
                }
                this.doPostHurtEffects(livingEntity);
                if (entity1 != null && livingEntity != entity1 && livingEntity instanceof Player && entity1 instanceof ServerPlayer serverPlayer && !this.isSilent()) {
                    serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }
        } else {
            if (!this.level().isClientSide) {
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) this.remove(RemovalReason.DISCARDED);
        this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
    }

    @Override
    public ItemStack getPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}