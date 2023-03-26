package mod.azure.doom.entity.tierboss;

import java.util.Random;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.KnockbackGoal;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
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
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArchMakyrEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(ArchMakyrEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ArchMakyrEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(true);

	public ArchMakyrEntity(EntityType<ArchMakyrEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		moveControl = new MoveHelperController(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) < 5)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) == 5)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("flying"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking_ranged", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking_aoe", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("flying_up", LoopType.PLAY_ONCE));
			return PlayState.STOP;
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
			level.broadcastEntityEvent(this, (byte) 60);
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
			if (source == damageSources().outOfWorld()) {
				setDeathState(5);
			}
			if (entityData.get(DEATH_STATE) > 5) {
				final AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(level, this.getX(), this.getY(), this.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				level.addFreshEntity(areaeffectcloudentity);
				goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
				setLastHurtMob(getLastHurtByMob());
				level.broadcastEntityEvent(this, (byte) 3);
			}
			if (entityData.get(DEATH_STATE) == 5) {
				super.die(source);
			}
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		goalSelector.addGoal(4, new RangedStrafeAttackGoal(this,
				new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.4, 0.8).setDamage(DoomConfig.archmaykr_ranged_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.archmaykr_phaseone_damage_boost : entityData.get(DEATH_STATE) == 2 ? DoomConfig.archmaykr_phasetwo_damage_boost : entityData.get(DEATH_STATE) == 3 ? DoomConfig.archmaykr_phasethree_damage_boost : entityData.get(DEATH_STATE) == 4 ? DoomConfig.archmaykr_phasefour_damage_boost : 0)), 1.0D, 1));
		targetSelector.addGoal(4, new KnockbackGoal(this, 1.0D));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	static class MoveHelperController extends MoveControl {
		private final ArchMakyrEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(ArchMakyrEntity ghast) {
			super(ghast);
			parentEntity = ghast;
		}

		@Override
		public void tick() {
			if (operation == MoveControl.Operation.MOVE_TO) {
				if (courseChangeCooldown-- <= 0) {
					courseChangeCooldown += parentEntity.getRandom().nextInt(5) + 2;
					Vec3 vector3d = new Vec3(wantedX - parentEntity.getX(), wantedY - parentEntity.getY(), wantedZ - parentEntity.getZ());
					final double d0 = vector3d.length();
					vector3d = vector3d.normalize();
					if (canReach(vector3d, Mth.ceil(d0))) {
						parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
					} else {
						operation = MoveControl.Operation.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
			AABB axisalignedbb = parentEntity.getBoundingBox();

			for (int i = 1; i < p_220673_2_; ++i) {
				axisalignedbb = axisalignedbb.move(p_220673_1_);
				if (!parentEntity.level.noCollision(parentEntity, axisalignedbb)) {
					return false;
				}
			}

			return true;
		}
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
		final FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
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
		if (hasCustomName()) {
			bossInfo.setName(getDisplayName());
		}
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

	public static boolean spawning(EntityType<ArchMakyrEntity> p_223337_0_, Level p_223337_1_, MobSpawnType reason, BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.55D).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MAX_HEALTH, DoomConfig.archmaykr_health).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public void knockback(double p_147241_, double p_147242_, double p_147243_) {
		super.knockback(0, 0, 0);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		setVariant(random.nextInt());
		return spawnDataIn;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.5F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.MAKYR_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MAKYR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MAKYR_DEATH;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof ArchMakyrEntity && e.tickCount < 1) {
				e.remove(RemovalReason.KILLED);
			}
			if (e instanceof Player) {
				if (!((Player) e).isCreative())
					if (!((Player) e).isSpectator())
						setTarget((LivingEntity) e);
			}
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
		super.customServerAiStep();
		bossInfo.setProgress(getHealth() / getMaxHealth());
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockpos = BlockPos.containing(x, y, z);
		boolean flag = false;
		double d0 = 0.0D;
		do {
			final BlockPos blockpos1 = blockpos.below();
			final BlockState blockstate = level.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
				if (!level.isEmptyBlock(blockpos)) {
					final BlockState blockstate1 = level.getBlockState(blockpos);
					final VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}
				flag = true;
				break;
			}
			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(maxY) - 1);

		if (flag) {
			final DoomFireEntity fang = new DoomFireEntity(level, x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.archmaykr_ranged_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.archmaykr_phaseone_damage_boost
					: entityData.get(DEATH_STATE) == 2 ? DoomConfig.archmaykr_phasetwo_damage_boost
							: entityData.get(DEATH_STATE) == 3 ? DoomConfig.archmaykr_phasethree_damage_boost

									: entityData.get(DEATH_STATE) == 4 ? DoomConfig.archmaykr_phasefour_damage_boost

											: 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			level.addFreshEntity(fang);
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
