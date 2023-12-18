package mod.azure.doom.entities.tierboss;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.ai.DemonFloatControl;
import mod.azure.doom.entities.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entities.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StayWithinDistanceOfAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ArchMakyrEntity extends DemonEntity implements SmartBrainOwner<ArchMakyrEntity>, DoomBoss {

    public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(ArchMakyrEntity.class,
            EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ArchMakyrEntity.class,
            EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(),
            BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(
            true);

    public ArchMakyrEntity(EntityType<ArchMakyrEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        moveControl = new DemonFloatControl(this);
    }

    public static @NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MOVEMENT_SPEED,
                0.55D).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MAX_HEALTH,
                MCDoom.config.archmaykr_health).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE,
                0.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0,
                        event -> event.setAndContinue((DoomAnimationsDefault.FLYING))).triggerableAnim("death",
                        this.getDeathState() < 5 ? DoomAnimationsDefault.DEATH_PHASEONE : DoomAnimationsDefault.DEATH))
                //Attack Stuff
                .add(new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).triggerableAnim(
                        "ranged", DoomAnimationsDefault.ATTACKING_AOE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (deathTime == 80 && entityData.get(DEATH_STATE) == 0) {
            setHealth(getMaxHealth());
            setDeathState(1);
            deathTime = 0;
        }
        if (deathTime == 80 && entityData.get(DEATH_STATE) == 1) {
            setHealth(getMaxHealth());
            setDeathState(2);
            deathTime = 0;
        }
        if (deathTime == 80 && entityData.get(DEATH_STATE) == 2) {
            setHealth(getMaxHealth());
            setDeathState(3);
            deathTime = 0;
        }
        if (deathTime == 80 && entityData.get(DEATH_STATE) == 3) {
            setHealth(getMaxHealth());
            setDeathState(4);
            deathTime = 0;
        }
        if (deathTime == 80 && entityData.get(DEATH_STATE) == 4) {
            setHealth(getMaxHealth());
            setDeathState(5);
            deathTime = 0;
        }
        if (deathTime == 40 && entityData.get(DEATH_STATE) == 5) {
            level().broadcastEntityEvent(this, (byte) 60);
            remove(Entity.RemovalReason.KILLED);
            dropExperience();
        }
    }

    public int getDeathState() {
        return entityData.get(DEATH_STATE);
    }

    public void setDeathState(int state) {
        entityData.set(DEATH_STATE, state);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if (!level().isClientSide) {
            if (source == damageSources().fellOutOfWorld()) setDeathState(5);
            if (entityData.get(DEATH_STATE) > 5) {
                final var areaeffectcloudentity = new AreaEffectCloud(level(), this.getX(), this.getY(), this.getZ());
                areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
                areaeffectcloudentity.setRadius(3.0F);
                areaeffectcloudentity.setDuration(55);
                areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
                level().addFreshEntity(areaeffectcloudentity);
                goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
                setLastHurtMob(Objects.requireNonNull(getLastHurtByMob()));
                level().broadcastEntityEvent(this, (byte) 3);
            }
            if (entityData.get(DEATH_STATE) == 5) super.die(source);
        }
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<ArchMakyrEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<ArchMakyrEntity>().setRadius(32).setPredicate(
                        (target, entity) -> target.isAlive() && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<ArchMakyrEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300),
                new FloatToSurfaceOfFluid<>(), new StayWithinDistanceOfAttackTarget<>().speedMod(2.25F),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<ArchMakyrEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>().stopIf(
                                target -> !target.isAlive() || target instanceof Player player && player.isCreative()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(2.0f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<ArchMakyrEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 2.05F),
                new DemonProjectileAttack<>(7).attackInterval(mob -> 240));
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this));
    }

    @Override
    public int getMaxFallDistance() {
        return 99;
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected boolean canRide(@NotNull Entity entity) {
        return false;
    }

    @Override
    public int getArmorValue() {
        return 15;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level worldIn) {
        final var flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    @Override
    public void travel(@NotNull Vec3 movementInput) {
        if (isInWater()) {
            moveRelative(0.02F, movementInput);
            move(MoverType.SELF, getDeltaMovement());
            this.setDeltaMovement(getDeltaMovement().scale(0.8F));
        } else if (isInLava()) {
            moveRelative(0.02F, movementInput);
            move(MoverType.SELF, getDeltaMovement());
            this.setDeltaMovement(getDeltaMovement().scale(0.5D));
        } else {
            final var ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
            var f = 0.91F;
            if (onGround()) f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
            final var f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (onGround()) f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
            moveRelative(onGround() ? 0.1F * f1 : 0.02F, movementInput);
            move(MoverType.SELF, getDeltaMovement());
            this.setDeltaMovement(getDeltaMovement().scale(f));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
        entityData.define(DEATH_STATE, 0);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
        if (hasCustomName()) bossInfo.setName(getDisplayName());
        setDeathState(compound.getInt("Phase"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Phase", getDeathState());
        tag.putInt("Variant", getVariant());
    }

    public int getVariant() {
        return Mth.clamp(entityData.get(VARIANT), 1, 2);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    public int getVariants() {
        return 2;
    }

    @Override
    public void knockback(double x, double y, double z) {
        super.knockback(0, 0, 0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        setVariant(getRandom().nextInt());
        return spawnDataIn;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
        return 1.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getMAKYR_AMBIENT();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getMAKYR_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getMAKYR_DEATH();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        final var aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
        level().getEntities(this, aabb).forEach(e -> {
            if (e instanceof ArchMakyrEntity && e.tickCount < 1) e.remove(RemovalReason.KILLED);
            if (e instanceof Player player && !(player.isCreative() && player.isSpectator()))
                setTarget((LivingEntity) e);
        });
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        bossInfo.setName(getDisplayName());
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        super.customServerAiStep();
        bossInfo.setProgress(getHealth() / getMaxHealth());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getAttckingState() > 1)
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 10, false, false));
    }

    public void spawnFlames(double x, double z, double maxY, double y) {
        var blockpos = BlockPos.containing(x, y, z);
        var flag = false;
        var d0 = 0.0D;
        do {
            final var blockpos1 = blockpos.below();
            final var blockstate = level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(level(), blockpos1, Direction.UP)) {
                if (!level().isEmptyBlock(blockpos)) {
                    final var blockstate1 = level().getBlockState(blockpos);
                    final var voxelshape = blockstate1.getCollisionShape(level(), blockpos);
                    if (!voxelshape.isEmpty()) d0 = voxelshape.max(Direction.Axis.Y);
                }
                flag = true;
                break;
            }
            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(maxY) - 1);

        if (flag) {
            final var fire = new DoomFireEntity(level(), x, blockpos.getY() + d0, z, 1, this,
                    MCDoom.config.archmaykr_ranged_damage + getExtraDamage());
            fire.setSecondsOnFire(tickCount);
            fire.setInvisible(false);
            level().addFreshEntity(fire);
        }
    }

    private float getExtraDamage() {
        switch (entityData.get(DEATH_STATE)) {
            case 1 -> {
                return MCDoom.config.archmaykr_phaseone_damage_boost;
            }
            case 2 -> {
                return MCDoom.config.archmaykr_phasetwo_damage_boost;
            }
            case 3 -> {
                return MCDoom.config.archmaykr_phasethree_damage_boost;
            }
            case 4 -> {
                return MCDoom.config.archmaykr_phasefour_damage_boost;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public void checkDespawn() {
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

}
