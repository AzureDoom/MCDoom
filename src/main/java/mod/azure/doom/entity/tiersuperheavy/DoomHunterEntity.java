package mod.azure.doom.entity.tiersuperheavy;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.registry.DoomSounds;
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
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoomHunterEntity extends DemonEntity implements GeoEntity {

	public int flameTimer;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(DoomHunterEntity.class, EntityDataSerializers.INT);

	public DoomHunterEntity(EntityType<DoomHunterEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("sled_death"));
			if (event.isMoving() && hurtDuration < 0)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("rockets"));
			if (entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("flamethrower"));
			if (entityData.get(STATE) == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("chainsaw"));
			event.getController().setAnimationSpeed(0.5);
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("phasechange"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.DOOMHUNTER_PHASECHANGE, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(4, new DoomHunterEntity.AttackGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}

	static class AttackGoal extends Goal {
		private final DoomHunterEntity parentEntity;
		protected int attackTimer = 0;

		public AttackGoal(DoomHunterEntity ghast) {
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
			parentEntity.setAttackingState(0);
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
				final Vec3 vector3d = parentEntity.getViewVector(1.0F);
				final double d0 = Math.min(livingentity.getY(), livingentity.getY());
				final double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
				final double d2 = livingentity.getX() - (parentEntity.getX() + vector3d.x * 2.0D);
				final double d3 = livingentity.getY(0.5D) - (0.5D + parentEntity.getY(0.5D));
				final double d4 = livingentity.getZ() - (parentEntity.getZ() + vector3d.z * 2.0D);
				final float f = (float) Mth.atan2(livingentity.getZ() - parentEntity.getZ(), livingentity.getX() - parentEntity.getX());
				final RocketMobEntity fireballentity = new RocketMobEntity(world, parentEntity, d2, d3, d4, DoomConfig.doomhunter_ranged_damage + (parentEntity.entityData.get(DEATH_STATE) == 1 ? DoomConfig.doomhunter_extra_phase_two_damage : 0));
				parentEntity.getNavigation().moveTo(livingentity, parentEntity.getDeathState() == 0 ? 0.75 : 1.0);
				if (attackTimer == 15) {
					if (parentEntity.distanceTo(livingentity) >= 3.0D) {
						if (parentEntity.entityData.get(DEATH_STATE) == 1) {
							for (int l = 0; l < 16; ++l) {
								final double d5 = 1.25D * (l + 1);
								final int j = 1 * l;
								parentEntity.spawnFlames(parentEntity.getX() + Mth.cos(f) * d5, parentEntity.getZ() + Mth.sin(f) * d5, d0, d1, f, j);
								parentEntity.setAttackingState(2);
							}
						}
						if (parentEntity.entityData.get(DEATH_STATE) == 0) {
							fireballentity.setPos(parentEntity.getX() + vector3d.x * 2.0D, parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 2.0D);
							world.addFreshEntity(fireballentity);
							parentEntity.setAttackingState(1);
						}
					} else {
						parentEntity.setAttackingState(3);
						parentEntity.doHurtTarget(livingentity);
					}
				}
				if (attackTimer >= 35) {
					parentEntity.setAttackingState(0);
					attackTimer = -15;
				}
			} else if (attackTimer > 0) {
				--attackTimer;
			}
			parentEntity.lookAt(livingentity, 30.0F, 30.0F);
		}

	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockpos = new BlockPos(x, y, z);
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
			final DoomFireEntity fang = new DoomFireEntity(level, x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.doomhunter_ranged_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.doomhunter_extra_phase_two_damage : 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.doomhunter_health).add(Attributes.FLYING_SPEED, 2.25D).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE, DoomConfig.doomhunter_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.55D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 6.05F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.DOOMHUNTER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.DOOMHUNTER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.DOOMHUNTER_DEATH;
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
		if (!level.isClientSide) {
			if (entityData.get(DEATH_STATE) == 0) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
			} else if (entityData.get(DEATH_STATE) == 1) {
				removeEffect(MobEffects.DAMAGE_BOOST);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
			}
		}
	}

	@Override
	public int getArmorValue() {
		return entityData.get(DEATH_STATE) == 1 ? 0 : (int) (getHealth() / getMaxHealth() * 100);
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
			if (source == DamageSource.OUT_OF_WORLD) {
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
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
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