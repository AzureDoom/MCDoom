package mod.azure.doom.entities.tierheavy;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.ai.DemonFlyControl;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
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

public class Revenant2016Entity extends DemonEntity implements SmartBrainOwner<Revenant2016Entity> {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Revenant2016Entity.class,
            EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public int flameTimer;

    public Revenant2016Entity(EntityType<Revenant2016Entity> entityType, Level worldIn) {
        super(entityType, worldIn);
        moveControl = new DemonFlyControl(this);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH,
                MCDoom.config.revenant_health).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.KNOCKBACK_RESISTANCE,
                0.6f).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(
                Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (dead || getHealth() < 0.01 || isDeadOrDying())
                return event.setAndContinue(DoomAnimationsDefault.DEATH);
            if (!isAggressive() && event.isMoving() && !(dead || getHealth() < 0.01 || isDeadOrDying()))
                return event.setAndContinue(DoomAnimationsDefault.WALKING);
            if (isAggressive() && event.isMoving() && !(dead || getHealth() < 0.01 || isDeadOrDying()))
                return event.setAndContinue(DoomAnimationsDefault.FLYING);
            if (!event.isCurrentAnimation(DoomAnimationsDefault.FLYING) && !event.isCurrentAnimation(
                    DoomAnimationsDefault.WALKING))
                return event.setAndContinue(DoomAnimationsDefault.IDLE);
            return PlayState.CONTINUE;
        })).add(new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).setSoundKeyframeHandler(
                event -> {
                    if (event.getKeyframeData().getSound().matches("attack"))
                        if (level().isClientSide())
                            level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                                    this.getVariant() == 11 ? mod.azure.doom.platform.Services.SOUNDS_HELPER.getREVENANT_DOOT() : mod.azure.doom.platform.Services.SOUNDS_HELPER.getROCKET_FIRING(),
                                    SoundSource.HOSTILE, 0.25F, 1.0F, false);
                }).triggerableAnim("ranged", DoomAnimationsDefault.RANGED).triggerableAnim("melee",
                DoomAnimationsDefault.MELEE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setVariant(tag.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    public int getVariant() {
        return Mth.clamp(entityData.get(VARIANT), 1, 11);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    public int getVariants() {
        return 11;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        setVariant(random.nextInt());
        return spawnDataIn;
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
    public List<ExtendedSensor<Revenant2016Entity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<Revenant2016Entity>().setPredicate(
                        (target, entity) -> target.isAlive() && entity.hasLineOfSight(
                                target) && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<Revenant2016Entity>());
    }

    @Override
    public BrainActivityGroup<Revenant2016Entity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300),
                new FloatToSurfaceOfFluid<>(), new StayWithinDistanceOfAttackTarget<>().speedMod(0.25F),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<Revenant2016Entity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<Revenant2016Entity>(
                        new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>().stopIf(
                                target -> !target.isAlive() || target instanceof Player player && player.isCreative()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<Revenant2016Entity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf(
                        (target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.05F),
                new DemonProjectileAttack<>(5).attackInterval(mob -> 80).attackDamage(
                        MCDoom.config.revenant_ranged_damage), new DemonMeleeAttack<>(5));
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (isAggressive()) {
            if (isInWater()) {
                moveRelative(0.02F, movementInput);
                move(MoverType.SELF, getDeltaMovement());
                this.setDeltaMovement(getDeltaMovement().scale(0.8F));
            } else if (isInLava()) {
                moveRelative(0.02F, movementInput);
                move(MoverType.SELF, getDeltaMovement());
                this.setDeltaMovement(getDeltaMovement().scale(0.5D));
            } else {
                final BlockPos ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
                float f = 0.91F;
                if (onGround()) {
                    f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
                }
                final float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (onGround()) {
                    f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
                }
                moveRelative(onGround() ? 0.1F * f1 : 0.02F, movementInput);
                move(MoverType.SELF, getDeltaMovement());
                this.setDeltaMovement(getDeltaMovement().scale(f));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level worldIn) {
        final var flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    protected void updateControlFlags() {
        final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
        goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
        super.updateControlFlags();
    }

    protected boolean shouldDrown() {
        return false;
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getREVENANT_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getREVENANT_DEATH();
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 7;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        flameTimer = (flameTimer + 1) % 8;
    }

    public int getFlameTimer() {
        return flameTimer;
    }

}