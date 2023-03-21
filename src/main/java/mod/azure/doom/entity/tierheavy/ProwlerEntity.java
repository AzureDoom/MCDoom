package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.SplittableRandom;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

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
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.FireballAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ProwlerEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ProwlerEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private int targetChangeTime;

	public ProwlerEntity(EntityType<? extends ProwlerEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && hurtTime == 0 && !isAggressive())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attack", LoopType.PLAY_ONCE));
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
		if (deathTime == 50) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(4, new ProwlerEntity.RangedStrafeAttackGoal(this, new FireballAttack(this, false).setProjectileOriginOffset(0.4, 0.4, 0.4).setDamage(DoomConfig.prowler_ranged_damage).setSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.4F + getRandom().nextFloat() * 0.35F), 1.0D, 50, 30, 15, 15F));
		goalSelector.addGoal(4, new DemonAttackGoal(this, 1.25D, 2));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new ProwlerEntity.FindPlayerGoal(this, this::isAngryAt));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
		targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	public class RangedStrafeAttackGoal extends Goal {
		private final ProwlerEntity entity;
		private float maxAttackDistance = 20;
		private int strafeTicks = 20;
		private int attackTime = -1;
		private int seeTime;
		private boolean strafingClockwise;
		private boolean strafingBackwards;
		private int strafingTime = -1;

		private final AbstractRangedAttack attack;

		public RangedStrafeAttackGoal(ProwlerEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn, int attackCooldownIn, int visibleTicksDelay, int strafeTicks, float maxAttackDistanceIn) {
			entity = mob;
			maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
			this.attack = attack;
			this.strafeTicks = strafeTicks;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this method as well.
		 */
		@Override
		public boolean canUse() {
			return entity.getTarget() != null;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean canContinueToUse() {
			return canUse() || !entity.getNavigation().isDone();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			super.start();
			entity.setAggressive(true);
			entity.setAttackingState(0);
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void stop() {
			super.stop();
			entity.setAggressive(false);
			entity.setAttackingState(0);
			seeTime = 0;
			attackTime = -1;
			entity.stopUsingItem();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			final LivingEntity livingentity = entity.getTarget();
			if (livingentity != null) {
				entity.lookAt(livingentity, 30.0F, 30.0F);
				final double distanceToTargetSq = entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
				final boolean inLineOfSight = entity.getSensing().hasLineOfSight(livingentity);
				if (inLineOfSight != seeTime > 0) {
					seeTime = 0;
				}

				if (inLineOfSight) {
					++seeTime;
				} else {
					--seeTime;
				}

				if (distanceToTargetSq <= maxAttackDistance && seeTime >= 20) {
					entity.getNavigation().stop();
					++strafingTime;
				} else {
					entity.getNavigation().moveTo(livingentity, 0.65F);
					entity.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
					strafingTime = -1;
				}

				if (strafingTime >= strafeTicks) {
					if (entity.getRandom().nextFloat() < 0.3D) {
						strafingClockwise = !strafingClockwise;
					}

					if (entity.getRandom().nextFloat() < 0.3D) {
						strafingBackwards = !strafingBackwards;
					}

					strafingTime = 0;
				}

				if (strafingTime > -1) {
					if (distanceToTargetSq > maxAttackDistance * 0.75F) {
						strafingBackwards = false;
					} else if (distanceToTargetSq < maxAttackDistance * 0.25F) {
						strafingBackwards = true;
					}

					entity.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
					entity.lookAt(livingentity, 30.0F, 30.0F);
				} else {
					entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
				}

				// attack
				attackTime++;
				if (attackTime == 1) {
					entity.setAttackingState(1);
				}
				if (attackTime == 4) {
					attack.shoot();
					entity.teleport();
					final boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
					entity.spawnLightSource(entity, isInsideWaterBlock);
				}
				if (attackTime >= 8) {
					entity.setAttackingState(0);
					attackTime = -5;
				}
			}
		}
	}

	static class FindPlayerGoal extends NearestAttackableTargetGoal<Player> {
		private final ProwlerEntity enderman;
		private Player pendingTarget;
		private int aggroTime;
		private int teleportTime;
		private final TargetingConditions startAggroTargetConditions;
		private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();

		public FindPlayerGoal(ProwlerEntity p_i241912_1_, @Nullable Predicate<LivingEntity> p_i241912_2_) {
			super(p_i241912_1_, Player.class, 10, false, false, p_i241912_2_);
			enderman = p_i241912_1_;
			startAggroTargetConditions = TargetingConditions.forCombat().range(getFollowDistance()).selector(p_32578_ -> p_i241912_1_.isLookingAtMe((Player) p_32578_));
		}

		@Override
		public boolean canUse() {
			pendingTarget = enderman.level.getNearestPlayer(startAggroTargetConditions, enderman);
			return pendingTarget != null;
		}

		@Override
		public void start() {
			aggroTime = 5;
			teleportTime = 0;
		}

		@Override
		public void stop() {
			pendingTarget = null;
			super.stop();
		}

		@Override
		public boolean canContinueToUse() {
			if (pendingTarget != null) {
				enderman.lookAt(pendingTarget, 10.0F, 10.0F);
				return true;
			} else {
				return target != null && continueAggroTargetConditions.test(enderman, target) ? true : super.canContinueToUse();
			}
		}

		@Override
		public void tick() {
			if (enderman.getTarget() == null) {
				super.setTarget((LivingEntity) null);
			}

			if (pendingTarget != null) {
				if (--aggroTime <= 0) {
					target = pendingTarget;
					pendingTarget = null;
					super.start();
				}
			} else {
				if (target != null && !enderman.isPassenger()) {
					if (target.distanceToSqr(enderman) > 256.0D && teleportTime++ >= 30 && enderman.teleportTowards(target)) {
						if (target.distanceToSqr(enderman) < 16.0D) {
							enderman.teleport();
						}
						teleportTime = 0;
					} else if (target.distanceToSqr(enderman) > 256.0D && teleportTime++ >= 30 && enderman.teleportTowards(target)) {
						teleportTime = 0;
					}
				}

				super.tick();
			}

		}
	}

	private boolean isLookingAtMe(Player player) {
		final Vec3 vector3d = player.getViewVector(1.0F).normalize();
		Vec3 vector3d1 = new Vec3(this.getX() - player.getX(), getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
		final double d0 = vector3d1.length();
		vector3d1 = vector3d1.normalize();
		final double d1 = vector3d.dot(vector3d1);
		return d1 > 1.0D - 0.025D / d0 ? player.hasLineOfSight(this) : false;
	}

	@Override
	public void aiStep() {
		if (level.isClientSide) {
			if (getVariant() == 1) {
				for (int i = 0; i < 2; ++i) {
					level.addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
				}
			} else {
				for (int i = 0; i < 2; ++i) {
					level.addParticle(ParticleTypes.COMPOSTER, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
				}
			}
		}

		jumping = false;
		if (!level.isClientSide) {
			updatePersistentAnger((ServerLevel) level, true);
		}

		super.aiStep();
	}

	@Override
	protected void customServerAiStep() {
		if (level.isDay() && tickCount >= targetChangeTime + 600) {
			final float f = getLightLevelDependentMagicValue();
			if (f > 0.5F && level.canSeeSky(blockPosition()) && random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				setTarget((LivingEntity) null);
				this.teleport();
			}
		}

		super.customServerAiStep();
	}

	protected boolean teleport() {
		if (!level.isClientSide() && isAlive()) {
			final double d0 = this.getX() + (random.nextDouble() - 0.5D) * 16.0D;
			final double d1 = this.getY() + (random.nextInt(64) - 16);
			final double d2 = this.getZ() + (random.nextDouble() - 0.5D) * 16.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleportTowards(Entity p_70816_1_) {
		Vec3 vec3 = new Vec3(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(), this.getZ() - p_70816_1_.getZ());
		vec3 = vec3.normalize();
		final double d1 = this.getX() + (random.nextDouble() - 0.5D) * 8.0D - vec3.x * 10.0D;
		final double d2 = this.getY() + (random.nextInt(16) - 8) - vec3.y * 10.0D;
		final double d3 = this.getZ() + (random.nextDouble() - 0.5D) * 8.0D - vec3.z * 10.0D;
		return this.teleport(d1, d2, d3);
	}

	private boolean teleport(double x, double y, double z) {
		final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

		while (blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		final BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
		final boolean flag = blockstate.getMaterial().blocksMotion();
		final boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			final boolean flag2 = randomTeleport(x, y, z, true);

			return flag2;
		} else {
			return false;
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.prowler_health).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE, DoomConfig.prowler_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
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
		return Mth.clamp(entityData.get(VARIANT), 1, 2);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final SplittableRandom random = new SplittableRandom();
		final int var = random.nextInt(0, 3);
		setVariant(var);
		return spawnDataIn;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (getVariant() == 2 && target instanceof LivingEntity) {
			((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), this);
		}
		return super.doHurtTarget(target);
	}

}