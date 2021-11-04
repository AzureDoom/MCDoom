package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.FireballAttack;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ProwlerEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);
	private int ageWhenTargetSet;

	public ProwlerEntity(EntityType<? extends ProwlerEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<ProwlerEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<ProwlerEntity>(this, "controller1", 0, this::predicate1));
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			if (world.isClient) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
				return PlayState.CONTINUE;
			}
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 50) {
			this.remove(Entity.RemovalReason.KILLED);
			if (world.isClient) {
			}
		}
	}

	public static boolean spawning(EntityType<ProwlerEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.prowler_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.prowler_melee_damage)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(4, new DemonAttackGoal(this, 1.0D, false, 2));
		this.goalSelector.add(4,
				new ProwlerEntity.RangedStrafeAttackGoal(this,
						new FireballAttack(this, false).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(config.prowler_ranged_damage).setSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F,
										1.4F + this.getRandom().nextFloat() * 0.35F),
						1.0D, 50, 30, 15, 15F).setMultiShot(3, 2));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(1, new ProwlerEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this));
		this.targetSelector.add(4, new UniversalAngerGoal<>(this, false));
	}

	public class RangedStrafeAttackGoal extends Goal {
		private final ProwlerEntity entity;
		private double moveSpeedAmp = 1;
		private int attackCooldown;
		private int visibleTicksDelay = 20;
		private float maxAttackDistance = 20;
		private int strafeTicks = 20;
		private int attackTime = -1;
		private int seeTime;
		private boolean strafingClockwise;
		private boolean strafingBackwards;
		private int strafingTime = -1;

		private AbstractRangedAttack attack;

		public RangedStrafeAttackGoal(ProwlerEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn,
				int attackCooldownIn, int visibleTicksDelay, int strafeTicks, float maxAttackDistanceIn) {
			this.entity = mob;
			this.moveSpeedAmp = moveSpeedAmpIn;
			this.attackCooldown = attackCooldownIn;
			this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
			this.attack = attack;
			this.visibleTicksDelay = visibleTicksDelay;
			this.strafeTicks = strafeTicks;
		}

		// use defaults
		public RangedStrafeAttackGoal(ProwlerEntity mob, AbstractRangedAttack attack, int attackCooldownIn) {
			this.entity = mob;
			this.attackCooldown = attackCooldownIn;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
			this.attack = attack;
		}

		private boolean multiShot = false;
		private int multiShotCount = 0;
		private int multiShotTickDelay = 0;

		private boolean multiShooting = false;
		private int multiShotsLeft = 0;
		private int multiShotTicker = 0;

		public RangedStrafeAttackGoal setMultiShot(int count, int tickDelay) {
			multiShot = true;
			multiShotCount = count;
			multiShotTickDelay = tickDelay;
			return this;
		}

		public boolean tickMultiShot() {
			if (multiShotsLeft > 0 && multiShotTicker == 0) {
				multiShotsLeft--;
				if (multiShotsLeft == 0)
					finishMultiShot();
				multiShotTicker = multiShotTickDelay;
				return true;
			}
			multiShotTicker--;
			return false;
		}

		public void beginMultiShooting() {
			multiShooting = true;
			multiShotsLeft = multiShotCount - 1;
			multiShotTicker = multiShotTickDelay;
		}

		public void finishMultiShot() {
			multiShooting = false;
			multiShotsLeft = 0;
		}

		public void setAttackInterval(int attackCooldownIn) {
			this.attackCooldown = attackCooldownIn;
		}

		public boolean canStart() {
			return this.entity.getTarget() != null;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean shouldExecute() {
			return this.entity.getTarget() != null;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldKeepRunning() {
			return (this.shouldExecute() || !this.entity.getNavigation().isIdle());
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void run() {
			super.start();
			this.entity.setAttacking(true);
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		public void finishRunning() {
			super.canStop();
			this.entity.setAttacking(false);
			this.entity.setAttackingState(0);
			this.seeTime = 0;
			this.attackTime = -1;
			this.entity.clearActiveItem();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				double distanceToTargetSq = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(),
						livingentity.getZ());
				boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
				if (inLineOfSight != this.seeTime > 0) {
					this.seeTime = 0;
				}

				if (inLineOfSight) {
					++this.seeTime;
				} else {
					if (multiShot)
						finishMultiShot();
					--this.seeTime;
				}

				if (distanceToTargetSq <= (double) this.maxAttackDistance && this.seeTime >= 20) {
					this.entity.getNavigation().stop();
					++this.strafingTime;
				} else {
					this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
					this.strafingTime = -1;
				}

				if (this.strafingTime >= strafeTicks) {
					if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
						this.strafingClockwise = !this.strafingClockwise;
					}

					if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
						this.strafingBackwards = !this.strafingBackwards;
					}

					this.strafingTime = 0;
				}

				if (this.strafingTime > -1) {
					if (distanceToTargetSq > (double) (this.maxAttackDistance * 0.75F)) {
						this.strafingBackwards = false;
					} else if (distanceToTargetSq < (double) (this.maxAttackDistance * 0.25F)) {
						this.strafingBackwards = true;
					}

					this.entity.getMoveControl().strafeTo(this.strafingBackwards ? -0.5F : 0.5F,
							this.strafingClockwise ? 0.5F : -0.5F);
					this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
				} else {
					this.entity.getLookControl().lookAt(livingentity, 30.0F, 30.0F);
				}

				// attack
				if (multiShooting) {
					if (tickMultiShot())
						this.attack.shoot();
					this.entity.teleportRandomly();
					return;
				}

				if (this.seeTime >= this.visibleTicksDelay) {
					if (this.attackTime >= this.attackCooldown) {
						this.attack.shoot();
						this.entity.teleportRandomly();
						this.attackTime = 0;
					} else
						this.attackTime++;
				}
				this.entity.setAttackingState(attackTime >= attackCooldown * 0.25 ? 1 : 0);
			}
		}
	}

	static class TeleportTowardsPlayerGoal extends FollowTargetGoal<PlayerEntity> {
		private final ProwlerEntity enderman;
		private PlayerEntity targetPlayer;
		private int lookAtPlayerWarmup;
		private int ticksSinceUnseenTeleport;
		private final TargetPredicate staringPlayerPredicate;
		private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();

		public TeleportTowardsPlayerGoal(ProwlerEntity enderman, @Nullable Predicate<LivingEntity> predicate) {
			super(enderman, PlayerEntity.class, 10, false, false, predicate);
			this.enderman = enderman;
			this.staringPlayerPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange())
					.setPredicate((playerEntity) -> {
						return enderman.isPlayerStaring((PlayerEntity) playerEntity);
					});
		}

		public boolean canStart() {
			this.targetPlayer = this.enderman.world.getClosestPlayer(this.staringPlayerPredicate, this.enderman);
			return this.targetPlayer != null;
		}

		public void start() {
			this.lookAtPlayerWarmup = 5;
			this.ticksSinceUnseenTeleport = 0;
		}

		public void stop() {
			this.targetPlayer = null;
			super.stop();
		}

		public boolean shouldContinue() {
			if (this.targetPlayer != null) {
				if (!this.enderman.isPlayerStaring(this.targetPlayer)) {
					return false;
				} else {
					this.enderman.lookAtEntity(this.targetPlayer, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.targetEntity != null && this.validTargetPredicate.test(this.enderman, this.targetEntity)
						? true
						: super.shouldContinue();
			}
		}

		public void tick() {
			if (this.enderman.getTarget() == null) {
				super.setTargetEntity((LivingEntity) null);
			}

			if (this.targetPlayer != null) {
				if (--this.lookAtPlayerWarmup <= 0) {
					this.targetEntity = this.targetPlayer;
					this.targetPlayer = null;
					super.start();
				}
			} else {
				if (this.targetEntity != null && !this.enderman.hasVehicle()) {
					if (this.enderman.isPlayerStaring((PlayerEntity) this.targetEntity)) {
						if (this.targetEntity.squaredDistanceTo(this.enderman) < 16.0D) {
							this.enderman.teleportRandomly();
						}

						this.ticksSinceUnseenTeleport = 0;
					} else if (this.targetEntity.squaredDistanceTo(this.enderman) > 256.0D
							&& this.ticksSinceUnseenTeleport++ >= 30 && this.enderman.teleportTo(this.targetEntity)) {
						this.ticksSinceUnseenTeleport = 0;
					}
				}

				super.tick();
			}

		}
	}

	private boolean isPlayerStaring(PlayerEntity player) {
		Vec3d vec3d = player.getRotationVec(1.0F).normalize();
		Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(),
				this.getZ() - player.getZ());
		double d = vec3d2.length();
		vec3d2 = vec3d2.normalize();
		double e = vec3d.dotProduct(vec3d2);
		return e > 1.0D - 0.025D / d ? player.canSee(this) : false;
	}

	@Override
	public void tickMovement() {
		if (this.world.isClient) {
			for (int i = 0; i < 2; ++i) {
				this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D,
						this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
						(this.random.nextDouble() - 0.5D) * 2.0D);
			}
		}

		this.jumping = false;
		if (!this.world.isClient) {
			this.tickAngerLogic((ServerWorld) this.world, true);
		}

		super.tickMovement();
	}

	@Override
	protected void mobTick() {
		if (this.world.isDay() && this.age >= this.ageWhenTargetSet + 600) {
			float f = this.getBrightnessAtEyes();
			if (f > 0.5F && this.world.isSkyVisible(this.getBlockPos())
					&& this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleportRandomly();
			}
		}

		super.mobTick();
	}

	protected boolean teleportRandomly() {
		if (!this.world.isClient() && this.isAlive()) {
			double d = this.getX() + (this.random.nextDouble() - 0.5D) * 16.0D;
			double e = this.getY() + (double) (this.random.nextInt(64) - 16);
			double f = this.getZ() + (this.random.nextDouble() - 0.5D) * 16.0D;
			return this.teleportTo(d, e, f);
		} else {
			return false;
		}
	}

	private boolean teleportTo(Entity entity) {
		Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5D) - entity.getEyeY(),
				this.getZ() - entity.getZ());
		vec3d = vec3d.normalize();
		double e = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
		double f = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3d.y * 16.0D;
		double g = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
		return this.teleportTo(e, f, g);
	}

	private boolean teleportTo(double x, double y, double z) {
		BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

		while (mutable.getY() > 0 && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
			mutable.move(Direction.DOWN);
		}

		BlockState blockState = this.world.getBlockState(mutable);
		boolean bl = blockState.getMaterial().blocksMovement();
		boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
		if (bl && !bl2) {
			boolean bl3 = this.teleport(x, y, z, true);
			return bl3;
		} else {
			return false;
		}
	}

	@Override
	public int tickTimer() {
		return age;
	}

}
