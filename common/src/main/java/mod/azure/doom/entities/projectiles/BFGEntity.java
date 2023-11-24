package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.tierambient.GoreNestEntity;
import mod.azure.doom.entities.tierboss.ArchMakyrEntity;
import mod.azure.doom.entities.tierboss.GladiatorEntity;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.entities.tierboss.MotherDemonEntity;
import mod.azure.doom.helper.CommonUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BFGEntity extends AbstractArrow implements GeoEntity {

    private static final EntityDataAccessor<Integer> TARGET_ENTITY = SynchedEntityData.defineId(BFGEntity.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
    Random rand = new Random();
    List<? extends String> whitelistEntries = Arrays.asList(MCDoom.config.bfg_damage_mob_whitelist);
    int randomIndex = rand.nextInt(whitelistEntries.size());
    ResourceLocation randomElement1 = new ResourceLocation(whitelistEntries.get(randomIndex));
    EntityType<?> randomElement = BuiltInRegistries.ENTITY_TYPE.get(randomElement1);
    private LivingEntity cachedBeamTarget;
    private LivingEntity shooter;
    private int idleTicks = 0;
    private int beamTicks;

    public BFGEntity(EntityType<? extends BFGEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public BFGEntity(Level world, LivingEntity owner) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getBFGEtntity(), owner, world);
        this.shooter = owner;
    }

    protected BFGEntity(EntityType<? extends BFGEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected BFGEntity(EntityType<? extends BFGEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof Player) this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected void tickDespawn() {
        if (tickCount >= 120) remove(RemovalReason.KILLED);
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
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        var isInsideWaterBlock = level().isWaterAt(blockPosition());
        CommonUtils.spawnLightSource(this, isInsideWaterBlock);
        if (this.tickCount >= 80) this.remove(RemovalReason.DISCARDED);
        var aabb = new AABB(this.blockPosition().above()).inflate(24D, 24D, 24D);
        this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
            var listEntity = randomElement.tryCast(e);
            if (!(e instanceof Player || e instanceof EnderDragon || e instanceof GoreNestEntity || e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity || e instanceof MotherDemonEntity) && (e instanceof Monster || e instanceof Slime || e instanceof Phantom || e instanceof DemonEntity || e instanceof Shulker || e instanceof Hoglin || (e == listEntity)) && e.isAlive()) {
                e.hurt(damageSources().explosion(this, shooter), MCDoom.config.bfgball_damage_aoe);
                this.setTargetedEntity(e.getId());
            }
            if (e instanceof EnderDragon enderDragon && e.isAlive()) {
                enderDragon.head.hurt(damageSources().playerAttack((Player) this.shooter), MCDoom.config.bfgball_damage_dragon * 0.3F);
                this.setTargetedEntity(e.getId());
            }
            if (e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity || e instanceof MotherDemonEntity && e.isAlive())
                e.hurt(damageSources().playerAttack((Player) this.shooter), MCDoom.config.bfgball_damage_aoe * 0.1F);
        });
    }

    @Override
    public ItemStack getPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    @Override
    public boolean isNoGravity() {
        return !this.isInWater();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.level().isClientSide) {
            this.doDamage();
            this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, MCDoom.config.enable_block_breaking ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
            this.remove(RemovalReason.KILLED);
        }
        this.playSound(mod.azure.doom.platform.Services.SOUNDS_HELPER.getBFG_HIT(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    public void doDamage() {
        var aabb = new AABB(this.blockPosition().above()).inflate(24D, 24D, 24D);
        this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
            var listEntity = randomElement.tryCast(e);
            if (!(e instanceof Player || e instanceof EnderDragon || e instanceof GoreNestEntity || e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity || e instanceof MotherDemonEntity) && (e instanceof Monster || e instanceof Slime || e instanceof Phantom || e instanceof DemonEntity || e instanceof Shulker || e instanceof Hoglin || (e == listEntity))) {
                if (this.isOnFire())
                    e.setSecondsOnFire(50);
                e.hurt(damageSources().playerAttack((Player) this.shooter), MCDoom.config.bfgball_damage);
                this.setTargetedEntity(e.getId());
                if (!this.level().isClientSide) {
                    var list1 = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
                    var areaeffectcloudentity = new AreaEffectCloud(e.level(), e.getX(), e.getY(), e.getZ());
                    areaeffectcloudentity.setParticle(ParticleTypes.TOTEM_OF_UNDYING);
                    areaeffectcloudentity.setRadius(3.0F);
                    areaeffectcloudentity.setDuration(10);
                    if (!list1.isEmpty()) {
                        for (var livingentity : list1) {
                            var d0 = this.distanceToSqr(livingentity);
                            if (d0 < 16.0D) areaeffectcloudentity.setPos(e.getX(), e.getEyeY(), e.getZ());
                        }
                    }
                    e.level().addFreshEntity(areaeffectcloudentity);
                }
            }
            if (e instanceof EnderDragon enderDragon && e.isAlive())
                enderDragon.head.hurt(damageSources().playerAttack((Player) this.shooter), MCDoom.config.bfgball_damage_dragon * 0.3F);
            if (e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity || e instanceof MotherDemonEntity && e.isAlive()) {
                if (this.isOnFire())
                    e.setSecondsOnFire(50);
                e.hurt(damageSources().playerAttack((Player) this.shooter), MCDoom.config.bfgball_damage * 0.1F);
            }
        });
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TARGET_ENTITY, 0);
    }

    public boolean hasTargetedEntity() {
        return this.entityData.get(TARGET_ENTITY) != 0;
    }

    @Nullable
    public LivingEntity getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        } else if (this.level().isClientSide) {
            if (this.cachedBeamTarget != null) return this.cachedBeamTarget;
            else {
                var entity = this.level().getEntity(this.entityData.get(TARGET_ENTITY));
                if (entity instanceof LivingEntity livingEntity) {
                    this.cachedBeamTarget = livingEntity;
                    return this.cachedBeamTarget;
                } else return null;
            }
        } else return this.getTarget();
    }

    private void setTargetedEntity(int entityId) {
        this.entityData.set(TARGET_ENTITY, entityId);
    }

    public float getBeamProgress(float tickDelta) {
        return (this.beamTicks + tickDelta) / this.getWarmupTime();
    }

    public int getWarmupTime() {
        return 80;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (TARGET_ENTITY.equals(key)) this.cachedBeamTarget = null;
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.cachedBeamTarget;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}