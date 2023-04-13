package mod.azure.doom.entity.tierboss;

import java.util.List;

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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
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

public class MotherDemonEntity extends DemonEntity implements SmartBrainOwner<MotherDemonEntity> {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(MotherDemonEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(true);

	public MotherDemonEntity(EntityType<MotherDemonEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		moveControl = new DemonFloatControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		var isDead = this.dead || this.getHealth() < 0.01 || this.isDeadOrDying();
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (this.swinging && !isDead)
				return event.setAndContinue(DoomAnimationsDefault.FIRE);
			return event.setAndContinue((isDead && event.getAnimatable().getDeathState() == 5) ? DoomAnimationsDefault.DEATH : (isDead && event.getAnimatable().getDeathState() < 5) ? DoomAnimationsDefault.DEATH_PHASEONE : DoomAnimationsDefault.MOVING);
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
		if (deathTime == 40 && entityData.get(DEATH_STATE) == 1) {
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
		if (!level.isClientSide) {
			if (source == damageSources().outOfWorld())
				setDeathState(1);
			if (entityData.get(DEATH_STATE) == 0) {
				final var areaeffectcloudentity = new AreaEffectCloud(level, this.getX(), this.getY(), this.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				level.addFreshEntity(areaeffectcloudentity);
				goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
				setLastHurtMob(getLastHurtByMob());
				level.broadcastEntityEvent(this, (byte) 3);
			}
			if (entityData.get(DEATH_STATE) == 1)
				super.die(source);
		}
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
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<MotherDemonEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<MotherDemonEntity>().setRadius(32).setPredicate((target, entity) -> target.isAlive() && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<MotherDemonEntity>());
	}

	@Override
	public BrainActivityGroup<MotherDemonEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StayWithinDistanceOfAttackTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<MotherDemonEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<MotherDemonEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
	}

	@Override
	public BrainActivityGroup<MotherDemonEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()), new SetWalkTargetToAttackTarget<>().speedMod(1.05F), new DemonProjectileAttack<>(7).attackInterval(mob -> 240), new AnimatableMeleeAttack<>(20));
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
	}

	@Override
	protected void updateControlFlags() {
		final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
		goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final var aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof MotherDemonEntity && e.tickCount < 1)
				e.remove(RemovalReason.KILLED);
			if (e instanceof Player)
				if (!((Player) e).isCreative())
					if (!((Player) e).isSpectator())
						setTarget((LivingEntity) e);
		});
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 9.05F;
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		var blockpos = BlockPos.containing(x, y, z);
		var flag = false;
		var d0 = 0.0D;
		do {
			final var blockpos1 = blockpos.below();
			final var blockstate = level.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
				if (!level.isEmptyBlock(blockpos)) {
					final var blockstate1 = level.getBlockState(blockpos);
					final var voxelshape = blockstate1.getCollisionShape(level, blockpos);
					if (!voxelshape.isEmpty())
						d0 = voxelshape.max(Direction.Axis.Y);
				}
				flag = true;
				break;
			}
			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(maxY) - 1);

		if (flag) {
			final var fang = new DoomFireEntity(level, x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.motherdemon_ranged_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.motherdemon_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.9f);
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.MOTHER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MOTHER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MOTHER_DEATH;
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
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DEATH_STATE, 0);
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
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (hasCustomName())
			bossInfo.setName(getDisplayName());
		setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", getDeathState());
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
