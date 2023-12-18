package mod.azure.doom.entities.tiersuperheavy;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
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

public class MarauderEntity extends DemonEntity implements SmartBrainOwner<MarauderEntity> {

    public static final EntityDataAccessor<Boolean> SPAWN = SynchedEntityData.defineId(MarauderEntity.class,
            EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public MarauderEntity(EntityType<MarauderEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH,
                MCDoom.config.marauder_health).add(Attributes.ATTACK_DAMAGE, MCDoom.config.marauder_axe_damage).add(
                Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.25D).add(
                Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (event.getAnimatable().isSpawn()) return event.setAndContinue(DoomAnimationsDefault.SPAWN);
            if (event.isMoving() && this.walkAnimation.speed() <= 0.35F)
                return event.setAndContinue(DoomAnimationsDefault.WALK);
            if (this.walkAnimation.speed() > 0.35F) return event.setAndContinue(DoomAnimationsDefault.RUN);
            if (dead || getHealth() < 0.01 || isDeadOrDying()) return event.setAndContinue(DoomAnimationsDefault.DEATH);
            return event.setAndContinue(DoomAnimationsDefault.IDLE);
        }).setSoundKeyframeHandler(event -> {
            if (level().isClientSide()) {
                if (event.getKeyframeData().getSound().matches("walk"))
                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getPINKY_STEP(), SoundSource.HOSTILE, 0.25F,
                            1.0F, false);
                if (event.getKeyframeData().getSound().matches("axe"))
                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getCRUCIBLE_AXE_RIGHT(), SoundSource.HOSTILE,
                            0.25F, 1.0F, false);
                if (event.getKeyframeData().getSound().matches("portal"))
                    level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PORTAL_AMBIENT,
                            SoundSource.AMBIENT, 0.5F, 1.0F, false);
            }
        }).triggerableAnim("death", DoomAnimationsDefault.DEATH)).add(
                new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).setSoundKeyframeHandler(
                        event -> {
                            if (level().isClientSide()) {
                                if (event.getKeyframeData().getSound().matches("attack"))
                                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getSUPER_SHOTGUN_SHOOT(),
                                            SoundSource.HOSTILE, 0.25F, 1.0F, false);
                                if (event.getKeyframeData().getSound().matches("axe_hit"))
                                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getCRUCIBLE_STAB(),
                                            SoundSource.HOSTILE, 0.25F, 1.0F, false);
                                if (event.getKeyframeData().getSound().matches("slash"))
                                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getCRUCIBLE_AXE_LEFT(),
                                            SoundSource.HOSTILE, 0.25F, 1.0F, false);
                            }
                        }).triggerableAnim("ranged",
                        RawAnimation.begin().then("shoot", LoopType.PLAY_ONCE)).triggerableAnim("death",
                        DoomAnimationsDefault.DEATH).triggerableAnim("slash",
                        RawAnimation.begin().then("energy_slash", LoopType.PLAY_ONCE)).triggerableAnim("cut",
                        RawAnimation.begin().then("axe_cut", LoopType.PLAY_ONCE)).triggerableAnim("hook",
                        RawAnimation.begin().then("hook", LoopType.PLAY_ONCE)).triggerableAnim("melee",
                        RawAnimation.begin().then("axe_attack", LoopType.PLAY_ONCE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<MarauderEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<MarauderEntity>().setPredicate(
                        (target, entity) -> target.isAlive() && entity.hasLineOfSight(
                                target) && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<MarauderEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>().startCondition(entity -> !this.isSpawn()),
                new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(),
                new MoveToWalkTarget<>().startCondition(entity -> !this.isSpawn()));
    }

    @Override
    public BrainActivityGroup<MarauderEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<>(
                new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                new SetPlayerLookTarget<>().startCondition(
                        target -> !this.isSpawn() && !target.isAlive() || target instanceof Player player && player.isCreative()),
                new SetRandomLookTarget<>().startCondition(entity -> !this.isSpawn())), new OneRandomBehaviour<>(
                new SetRandomWalkTarget<>().setRadius(20).speedModifier(0.5f).startCondition(entity -> !this.isSpawn()),
                new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<MarauderEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf(
                        (target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.5F).startCondition(
                        entity -> !this.isSpawn()),
                new DemonProjectileAttack<>(10).attackInterval(mob -> 90).attackDamage(
                        MCDoom.config.marauder_ssgdamage), new DemonMeleeAttack<>(10));
    }

    public boolean isSpawn() {
        return this.entityData.get(SPAWN);
    }

    public void setSpawnState(boolean state) {
        this.entityData.set(SPAWN, state);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SPAWN, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("isSpawn", this.isSpawn());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        setSpawnState(nbt.getBoolean("isSpawn"));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount <= 1) this.setSpawnState(true);
        if (this.isSpawn() && this.tickCount > 280) {
            this.setSpawnState(false);
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false));
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return (!isSpawn() || source == damageSources().genericKill()) && super.hurt(source, amount);
    }

    @Override
    public void aiStep() {
        if (level().isClientSide && (!this.isSpawn())) {
            for (var i = 0; i < 2; ++i) {
                level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D),
                        (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
            }
        }
        jumping = false;
        if (!level().isClientSide) updatePersistentAnger((ServerLevel) level(), true);
        super.aiStep();
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        if (level().isDay() && tickCount >= targetChangeTime + 600) {
            final var f = getLightLevelDependentMagicValue();
            if (f > 0.5F && level().canSeeSky(blockPosition()) && random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                teleportRandomly();
            }
        }
        super.customServerAiStep();
    }

    public void teleportRandomly() {
        if (!level().isClientSide() && isAlive()) {
            final double d0 = this.getX() + (random.nextDouble() - 0.5D) * 10.0D;
            final double d1 = this.getY() + (random.nextInt(64) - 10);
            final double d2 = this.getZ() + (random.nextDouble() - 0.5D) * 10.0D;
            teleport(d0, d1, d2);
        }
    }

    private void teleport(double x, double y, double z) {
        final var mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);

        while (mutableBlockPos.getY() > level().getMinBuildHeight() && !level().getBlockState(
                mutableBlockPos).blocksMotion()) mutableBlockPos.move(Direction.DOWN);

        final var blockstate = level().getBlockState(mutableBlockPos);
        if (blockstate.blocksMotion() && !blockstate.getFluidState().is(FluidTags.WATER)) {
            randomTeleport(x, y, z, true);
        }
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getZOMBIEMAN_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getZOMBIEMAN_DEATH();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }
}