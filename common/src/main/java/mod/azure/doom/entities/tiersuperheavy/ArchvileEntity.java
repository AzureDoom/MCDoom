package mod.azure.doom.entities.tiersuperheavy;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
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

public class ArchvileEntity extends DemonEntity implements SmartBrainOwner<ArchvileEntity> {

    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ArchvileEntity.class,
            EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public ArchvileEntity(EntityType<ArchvileEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH,
                MCDoom.config.archvile_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED,
                0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (event.isMoving() && event.getAnimatable().getAttckingState() == 0)
                return event.setAndContinue(DoomAnimationsDefault.WALKING);
            if (dead || getHealth() < 0.01 || isDeadOrDying()) return event.setAndContinue(DoomAnimationsDefault.DEATH);
            return event.setAndContinue(DoomAnimationsDefault.IDLE);
        }).triggerableAnim("death", DoomAnimationsDefault.DEATH).setSoundKeyframeHandler(event -> {
            if (level().isClientSide()) {
                if (event.getKeyframeData().getSound().matches("walk"))
                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getPINKY_STEP(), SoundSource.HOSTILE, 0.25F,
                            1.0F, false);
                if (event.getKeyframeData().getSound().matches("attack"))
                    level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                            mod.azure.doom.platform.Services.SOUNDS_HELPER.getARCHVILE_SCREAM(), SoundSource.HOSTILE,
                            0.25F, 1.0F, false);
            }
        }).triggerableAnim("ranged", DoomAnimationsDefault.ATTACKING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (!level().isClientSide) this.level().getEntitiesOfClass(Monster.class,
                new AABB(blockPosition().above()).inflate(64D, 64D, 64D)).forEach(e -> {
            if (e.isAlive()) {
                e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 10000, true, false));
                e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2, 10000, true, false));
                e.setGlowingTag(false);
            }
        });
        super.tickDeath();
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
        return Mth.clamp(entityData.get(VARIANT), 1, 2);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, variant);
    }

    public int getVariants() {
        return 2;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        setVariant(random.nextInt());
        return spawnDataIn;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        flameTimer = (flameTimer + 1) % 8;
    }

    public int getFlameTimer() {
        return flameTimer;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    protected boolean shouldDrown() {
        return false;
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<ArchvileEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<ArchvileEntity>().setPredicate(
                        (target, entity) -> target.isAlive() && entity.hasLineOfSight(
                                target) && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<ArchvileEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300),
                new FloatToSurfaceOfFluid<>(), new StrafeTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<ArchvileEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<ArchvileEntity>(
                        new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>().stopIf(
                                target -> !target.isAlive() || target instanceof Player player && player.isCreative()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<ArchvileEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf(
                        (target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.05F),
                new DemonProjectileAttack<>(5).attackInterval(mob -> 180).attackDamage(
                        MCDoom.config.baron_ranged_damage), new DemonMeleeAttack<>(5));
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    protected void updateControlFlags() {
        final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
        goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
        super.updateControlFlags();
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
                    MCDoom.config.archvile_ranged_damage);
            fang.setSecondsOnFire(tickCount);
            fang.setInvisible(false);
            level().addFreshEntity(fang);
        }
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getARCHVILE_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getARCHVILE_DEATH();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

}