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
import mod.azure.doom.entity.projectiles.entity.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import mod.azure.doom.util.registry.DoomEntities;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MotherDemonEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(MotherDemonEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(true);

	public MotherDemonEntity(EntityType<MotherDemonEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) == 0)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("shooting", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("fire", LoopType.PLAY_ONCE));
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
			if (source == damageSources().outOfWorld()) {
				setDeathState(1);
			}
			if (entityData.get(DEATH_STATE) == 0) {
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
			if (entityData.get(DEATH_STATE) == 1) {
				super.die(source);
			}
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
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, IronGolem.class, 8.0F));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		applyEntityAI();
	}

	protected void applyEntityAI() {
		goalSelector.addGoal(1, new MotherDemonEntity.AttackGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
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
		final AABB aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof MotherDemonEntity && e.tickCount < 1) {
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
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 9.05F;
	}

	static class AttackGoal extends Goal {
		private final MotherDemonEntity parentEntity;
		protected int attackTimer = 0;

		public AttackGoal(MotherDemonEntity ghast) {
			parentEntity = ghast;
		}

		@Override
		public boolean canUse() {
			return parentEntity.getTarget() != null;
		}

		@Override
		public void start() {
			super.start();
			parentEntity.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			parentEntity.setAggressive(false);
			parentEntity.setAttackingState(0);
			attackTimer = -1;
		}

		@Override
		public void tick() {
			final LivingEntity livingentity = parentEntity.getTarget();
			if (parentEntity.hasLineOfSight(livingentity)) {
				final Level world = parentEntity.level;
				++attackTimer;
				final Random rand = new Random();
				final Vec3 vector3d = parentEntity.getViewVector(1.0F);
				final double d0 = Math.min(livingentity.getY(), livingentity.getY());
				final double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
				final double d2 = livingentity.getX() - (parentEntity.getX() + vector3d.x * 2.0D);
				final double d3 = livingentity.getY(0.5D) - (0.5D + parentEntity.getY(0.5D));
				final double d4 = livingentity.getZ() - (parentEntity.getZ() + vector3d.z * 2.0D);
				final float f = (float) Mth.atan2(livingentity.getZ() - parentEntity.getZ(), livingentity.getX() - parentEntity.getX());
				final CustomFireballEntity fireballentity = new CustomFireballEntity(world, parentEntity, d2, d3, d4, DoomConfig.motherdemon_ranged_damage + (parentEntity.entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
				final CustomFireballEntity fireballentity1 = new CustomFireballEntity(world, parentEntity, d2, d3, d4, DoomConfig.motherdemon_ranged_damage + (parentEntity.entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
				final CustomFireballEntity fireballentity2 = new CustomFireballEntity(world, parentEntity, d2, d3, d4, DoomConfig.motherdemon_ranged_damage + (parentEntity.entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
				parentEntity.getNavigation().moveTo(livingentity, 1.5D);
				if (attackTimer == 15) {
					if (parentEntity.getHealth() <= parentEntity.getMaxHealth() * 0.50) {
						for (int l = 0; l < 32; ++l) {
							final float f1 = f + l * (float) Math.PI * 0.4F;
							for (int y = 0; y < 5; ++y) {
								parentEntity.spawnFlames(parentEntity.getX() + Mth.cos(f1) * rand.nextDouble() * 11.5D, parentEntity.getZ() + Mth.sin(f1) * rand.nextDouble() * 11.5D, d0, d1, f1, 0);
							}
							parentEntity.level.playLocalSound(parentEntity.getX(), parentEntity.getY(), parentEntity.getZ(), DoomSounds.MOTHER_ATTACK, SoundSource.HOSTILE, 1.0F, 1.0F, true);
							parentEntity.setAttackingState(2);
						}
						livingentity.setDeltaMovement(livingentity.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
						final TentacleEntity lost_soul = DoomEntities.TENTACLE.create(world);
						lost_soul.moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 0, 0);
						world.addFreshEntity(lost_soul);
					} else {
						fireballentity.setPos(parentEntity.getX() + vector3d.x * 1.0D, parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity);
						fireballentity1.setPos(parentEntity.getX() + 3 + vector3d.x * 1.0D, parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity1);
						fireballentity2.setPos(parentEntity.getX() - 3 + vector3d.x * 1.0D, parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity2);
						parentEntity.level.playLocalSound(parentEntity.getX(), parentEntity.getY(), parentEntity.getZ(), DoomSounds.MOTHER_ATTACK, SoundSource.HOSTILE, 1.0F, 1.0F, true);
						final TentacleEntity lost_soul = DoomEntities.TENTACLE.create(world);
						lost_soul.moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 0, 0);
						world.addFreshEntity(lost_soul);
						parentEntity.setAttackingState(1);
					}
				}
				if (attackTimer >= 30) {
					parentEntity.setAttackingState(0);
					attackTimer = -5;
				}
			} else if (attackTimer > 0) {
				--attackTimer;
			}
			parentEntity.lookAt(livingentity, 30.0F, 30.0F);
		}

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
			final DoomFireEntity fang = new DoomFireEntity(level, x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.motherdemon_ranged_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
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
		super.customServerAiStep();
		bossInfo.setProgress(getHealth() / getMaxHealth());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (hasCustomName()) {
			bossInfo.setName(getDisplayName());
		}
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
