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
import mod.azure.doom.entities.ai.DemonFloatControl;
import mod.azure.doom.entities.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import mod.azure.doom.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
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
import java.util.Objects;

public class PainEntity extends DemonEntity implements SmartBrainOwner<PainEntity> {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PainEntity.class,
            EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public PainEntity(EntityType<? extends PainEntity> type, Level worldIn) {
        super(type, worldIn);
        moveControl = new DemonFloatControl(this);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.ATTACK_DAMAGE,
                MCDoom.config.lost_soul_melee_damage).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(
                Attributes.MAX_HEALTH, MCDoom.config.pain_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(
                Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (event.isMoving() && hurtTime == 0 && !isAggressive())
                return event.setAndContinue(DoomAnimationsDefault.WALKING);
            if (dead || getHealth() < 0.01 || isDeadOrDying()) return event.setAndContinue(DoomAnimationsDefault.DEATH);
            return event.setAndContinue(DoomAnimationsDefault.IDLE);
        }).triggerableAnim("death", DoomAnimationsDefault.DEATH)).add(
                new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).triggerableAnim(
                        "ranged", DoomAnimationsDefault.ATTACKING).triggerableAnim("melee",
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
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    public int getVariant() {
        return Mth.clamp(entityData.get(VARIANT), 1, 3);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    public int getVariants() {
        return 3;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        setVariant(this.getRandom().nextInt());
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
    public List<ExtendedSensor<PainEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<PainEntity>().setPredicate(
                        (target, entity) -> target.isAlive() && entity.hasLineOfSight(
                                target) && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<PainEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300),
                new FloatToSurfaceOfFluid<>(), new StayWithinDistanceOfAttackTarget<>().speedMod(0.25F),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<PainEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<PainEntity>(
                        new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>().stopIf(
                                target -> !target.isAlive() || target instanceof Player player && player.isCreative()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<PainEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf(
                        (target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.05F),
                new DemonProjectileAttack<>(7).attackInterval(mob -> 80), new DemonMeleeAttack<>(5));
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this));
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (deathTime == 30) {
            remove(RemovalReason.KILLED);
            dropExperience();
            if (!level().isClientSide()) {
                final var lostSoul = Services.ENTITIES_HELPER.getLostSoulEntity().create(level());
                Objects.requireNonNull(lostSoul).moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
                level().addFreshEntity(lostSoul);
                final var lostSoul1 = Services.ENTITIES_HELPER.getLostSoulEntity().create(level());
                Objects.requireNonNull(lostSoul1).moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
                level().addFreshEntity(lostSoul1);
                final var lostSoul2 = Services.ENTITIES_HELPER.getLostSoulEntity().create(level());
                Objects.requireNonNull(lostSoul2).moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
                level().addFreshEntity(lostSoul2);
            }
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
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
    public boolean onClimbable() {
        return false;
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
        return 1.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getPAIN_AMBIENT();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getPAIN_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getPAIN_DEATH();
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }

}