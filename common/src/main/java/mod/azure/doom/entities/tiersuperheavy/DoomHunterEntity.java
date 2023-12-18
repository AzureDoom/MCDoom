package mod.azure.doom.entities.tiersuperheavy;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
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

public class DoomHunterEntity extends DemonEntity implements SmartBrainOwner<DoomHunterEntity> {

    public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(DoomHunterEntity.class,
            EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public DoomHunterEntity(EntityType<DoomHunterEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH,
                MCDoom.config.doomhunter_health).add(Attributes.FLYING_SPEED, 2.25D).add(
                Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE,
                MCDoom.config.doomhunter_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.55D).add(
                Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if ((dead || getHealth() < 0.01 || isDeadOrDying()) && event.getAnimatable().getDeathState() == 1)
                return event.setAndContinue(DoomAnimationsDefault.DEATH);
            if (dead || getHealth() < 0.01 || isDeadOrDying())
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("sled_death"));
            if (event.isMoving() && hurtDuration < 0 && event.getAnimatable().getAttckingState() == 0)
                return event.setAndContinue(DoomAnimationsDefault.WALKING);
            event.getController().setAnimationSpeed(0.5);
            return event.setAndContinue(DoomAnimationsDefault.IDLE);
        }).triggerableAnim("death", DoomAnimationsDefault.DEATH).setSoundKeyframeHandler(event -> {
            if (event.getKeyframeData().getSound().matches("phasechange") && (level().isClientSide()))
                level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                        mod.azure.doom.platform.Services.SOUNDS_HELPER.getDOOMHUNTER_PHASECHANGE(), SoundSource.HOSTILE,
                        0.25F, 1.0F, false);
        })).add(new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).triggerableAnim("melee",
                DoomAnimationsDefault.CHIANSAW).triggerableAnim("rocket",
                DoomAnimationsDefault.ROCKETS).triggerableAnim("flames", DoomAnimationsDefault.FLAMETHROWER));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        super.customServerAiStep();
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<DoomHunterEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<DoomHunterEntity>().setPredicate(
                        (target, entity) -> target.isAlive() && entity.hasLineOfSight(
                                target) && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<DoomHunterEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300),
                new FloatToSurfaceOfFluid<>(), new StrafeTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<DoomHunterEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<DoomHunterEntity>(
                        new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>().stopIf(
                                target -> !target.isAlive() || target instanceof Player player && player.isCreative()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<DoomHunterEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf(
                        (target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.05F),
                new DemonProjectileAttack<>(7).attackInterval(mob -> 80),
                new DemonMeleeAttack<>(8).attackInterval(mob -> 80));
    }

    @Override
    protected void registerGoals() {
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
            final var fang = new DoomFireEntity(level(), x, blockpos.getY() + d0, z, 1, this,
                    MCDoom.config.doomhunter_ranged_damage + (this.getDeathState() == 1 ? MCDoom.config.doomhunter_extra_phase_two_damage : 0));
            fang.setSecondsOnFire(tickCount);
            fang.setInvisible(false);
            level().addFreshEntity(fang);
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
        return 6.05F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getDOOMHUNTER_AMBIENT();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getDOOMHUNTER_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getDOOMHUNTER_DEATH();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public int getFlameTimer() {
        return flameTimer;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        flameTimer = (flameTimer + 1) % 8;
        ++tickCount;
        if (!level().isClientSide) {
            if (this.getDeathState() == 0) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
            } else if (this.getDeathState() == 1) {
                removeEffect(MobEffects.DAMAGE_BOOST);
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
            }
        }
    }

    @Override
    public int getArmorValue() {
        return this.getDeathState() == 1 ? 0 : (int) (getHealth() / getMaxHealth() * 100);
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (deathTime == 80 && this.getDeathState() == 0) {
            setHealth(getMaxHealth());
            setDeathState(1);
            deathTime = 0;
        }
        if (deathTime == 40 && this.getDeathState() == 1) {
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
            if (source == damageSources().fellOutOfWorld()) {
                setDeathState(1);
            }
            if (this.getDeathState() == 0) {
                final var areaeffectcloudentity = new AreaEffectCloud(level(), this.getX(), this.getY(), this.getZ());
                areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
                areaeffectcloudentity.setRadius(3.0F);
                areaeffectcloudentity.setDuration(55);
                areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
                level().addFreshEntity(areaeffectcloudentity);
                setLastHurtMob(Objects.requireNonNull(getLastHurtByMob()));
                level().broadcastEntityEvent(this, (byte) 3);
            }
            if (this.getDeathState() == 1) super.die(source);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setDeathState(compound.getInt("Phase"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Phase", getDeathState());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DEATH_STATE, 0);
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