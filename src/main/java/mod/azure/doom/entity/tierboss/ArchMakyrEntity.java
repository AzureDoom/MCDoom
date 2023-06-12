package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.Random;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.DoomAnimationsDefault;
import mod.azure.doom.entity.ai.DemonFloatControl;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.task.DemonProjectileAttack;
import mod.azure.doom.util.registry.DoomSounds;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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

public class ArchMakyrEntity extends DemonEntity implements SmartBrainOwner<ArchMakyrEntity> {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(ArchMakyrEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ArchMakyrEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(true);

	public ArchMakyrEntity(EntityType<ArchMakyrEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		moveControl = new DemonFloatControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		var isDead = this.dead || this.getHealth() < 0.01 || this.isDeadOrDying();
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (this.getAttckingState() == 1 && !isDead)
				return event.setAndContinue(DoomAnimationsDefault.ATTACKING_AOE);
			return event.setAndContinue((isDead && this.getDeathState() == 5) ? DoomAnimationsDefault.DEATH : (isDead && this.getDeathState() < 5) ? DoomAnimationsDefault.DEATH_PHASEONE : DoomAnimationsDefault.FLYING);
		}));
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
	public void die(DamageSource source) {
		if (!level().isClientSide) {
			if (source == damageSources().genericKill())
				setDeathState(5);
			if (entityData.get(DEATH_STATE) > 5) {
				final var areaeffectcloudentity = new AreaEffectCloud(level(), this.getX(), this.getY(), this.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				level().addFreshEntity(areaeffectcloudentity);
				goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
				setLastHurtMob(getLastHurtByMob());
				level().broadcastEntityEvent(this, (byte) 3);
			}
			if (entityData.get(DEATH_STATE) == 5)
				super.die(source);
		}
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<ArchMakyrEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<ArchMakyrEntity>().setRadius(32).setPredicate((target, entity) -> target.isAlive() && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<ArchMakyrEntity>());
	}

	@Override
	public BrainActivityGroup<ArchMakyrEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StayWithinDistanceOfAttackTarget<>().speedMod(2.25F), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<ArchMakyrEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<ArchMakyrEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(2.0f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
	}

	@Override
	public BrainActivityGroup<ArchMakyrEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()), new SetWalkTargetToAttackTarget<>().speedMod(2.05F), new DemonProjectileAttack<>(7).attackInterval(mob -> 240));
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
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
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
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
	protected boolean canRide(Entity p_184228_1_) {
		return false;
	}

	@Override
	public int getArmorValue() {
		return 15;
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		final var flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

	@Override
	protected void updateControlFlags() {
		final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
		goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(VARIANT, 0);
		entityData.define(DEATH_STATE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setVariant(compound.getInt("Variant"));
		if (hasCustomName())
			bossInfo.setName(getDisplayName());
		setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
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

	public static boolean spawning(EntityType<ArchMakyrEntity> entity, Level level, MobSpawnType reason, BlockPos pos, Random random) {
		return level.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.55D).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MAX_HEALTH, DoomConfig.SERVER.archmaykr_health.get()).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public void knockback(double x, double y, double z) {
		super.knockback(0, 0, 0);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		setVariant(getRandom().nextInt());
		return spawnDataIn;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.5F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.MAKYR_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MAKYR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MAKYR_DEATH.get();
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final var aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof ArchMakyrEntity && e.tickCount < 1)
				e.remove(RemovalReason.KILLED);
			if (e instanceof Player)
				if (!((Player) e).isCreative())
					if (!((Player) e).isSpectator())
						setTarget((LivingEntity) e);
		});
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
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
		attackstatetimer++;
		if (this.getAttckingState() > 1 && !this.isDeadOrDying()) {
			if (attackstatetimer >= 28) {
				attackstatetimer = 0;
				this.setAttackingState(0);
			}
		}
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
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
					if (!voxelshape.isEmpty())
						d0 = voxelshape.max(Direction.Axis.Y);
				}
				flag = true;
				break;
			}
			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(maxY) - 1);

		if (flag) {
			final var fire = new DoomFireEntity(level(), x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.SERVER.archmaykr_ranged_damage.get().floatValue() + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.SERVER.archmaykr_phaseone_damage_boost.get().floatValue() : entityData.get(DEATH_STATE) == 2 ? DoomConfig.SERVER.archmaykr_phasetwo_damage_boost.get().floatValue() : entityData.get(DEATH_STATE) == 3 ? DoomConfig.SERVER.archmaykr_phasethree_damage_boost.get().floatValue() : entityData.get(DEATH_STATE) == 4 ? DoomConfig.SERVER.archmaykr_phasefour_damage_boost.get().floatValue() : 0));
			fire.setSecondsOnFire(tickCount);
			fire.setInvisible(false);
			level().addFreshEntity(fire);
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
