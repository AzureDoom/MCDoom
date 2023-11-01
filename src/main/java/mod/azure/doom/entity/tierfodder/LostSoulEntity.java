package mod.azure.doom.entity.tierfodder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.DoomAnimationsDefault;
import mod.azure.doom.entity.ai.DemonFloatControl;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.task.DemonMeleeAttack;
import mod.azure.doom.util.registry.DoomSounds;
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

import java.util.List;

public class LostSoulEntity extends DemonEntity implements SmartBrainOwner<LostSoulEntity> {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(LostSoulEntity.class, EntityDataSerializers.INT);
	public int explosionPower = 1;
	public int flameTimer;

	public LostSoulEntity(EntityType<? extends LostSoulEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(4.0F);
		moveControl = new DemonFloatControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() || this.swinging)
				return event.setAndContinue(DoomAnimationsDefault.WALKING);
			return event.setAndContinue(DoomAnimationsDefault.IDLE);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			return PlayState.STOP;
		}).triggerableAnim("melee", DoomAnimationsDefault.MELEE));
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
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setVariant(tag.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final var variant = this.getRandom().nextInt(0, 4);
		setVariant(variant);
		return spawnDataIn;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomMod.config.lost_soul_health).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, DoomMod.config.lost_soul_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
		super.customServerAiStep();
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<LostSoulEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<LostSoulEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<LostSoulEntity>());
	}

	@Override
	public BrainActivityGroup<LostSoulEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<LostSoulEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<LostSoulEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.1f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
	}

	@Override
	public BrainActivityGroup<LostSoulEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()), new SetWalkTargetToAttackTarget<>().speedMod((owner, entity) -> 3.4F), new DemonMeleeAttack<>(5));
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
	}

	@Override
	public double getMeleeAttackRangeSqr(LivingEntity livingEntity) {
		return this.getBbWidth() * 1.5f * (this.getBbWidth() * 1.5f + livingEntity.getBbWidth());
	}

	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		var distance = this.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
		return distance <= this.getMeleeAttackRangeSqr(livingEntity);
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 5) {
			remove(RemovalReason.KILLED);
			dropExperience();
			if (!level().isClientSide)
				explode();
		}
	}

	protected void explode() {
		level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, Level.ExplosionInteraction.NONE);
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public boolean onClimbable() {
		return true;
	}

	@Override
	public void travel(Vec3 movementInput) {
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
			if (onGround())
				f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
			final var f1 = 0.16277137F / (f * f * f);
			f = 0.91F;
			if (onGround())
				f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
			moveRelative(onGround() ? 0.1F * f1 : 0.02F, movementInput);
			move(MoverType.SELF, getDeltaMovement());
			this.setDeltaMovement(getDeltaMovement().scale(f));
		}
		if (tickCount % 10 == 0)
			refreshDimensions();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 8;

		final var isInsideWaterBlock = level().isWaterAt(blockPosition());
		spawnLightSource(this, isInsideWaterBlock);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.LOST_SOUL_DEATH.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.LOST_SOUL_DEATH.get();
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return getVariant() == 3 ? EntityDimensions.scalable(1.0F, 1.5F) : super.getDimensions(pose);
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		final var flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

}