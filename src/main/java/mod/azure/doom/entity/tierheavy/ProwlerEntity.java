package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.config.DoomConfig.Server;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ProwlerEntity extends DemonEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	private int targetChangeTime;

	public static Server config = DoomConfig.SERVER;

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
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
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
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 50) {
			this.remove();
			if (level.isClientSide) {
			}
		}
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(4,
				new ProwlerEntity.RangedStrafeAttackGoal(this,
						new FireballAttack(this, false).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(config.prowler_ranged_damage.get().floatValue()).setSound(SoundEvents.BLAZE_SHOOT, 1.0F,
										1.4F + this.getRandom().nextFloat() * 0.35F),
						1.0D, 50, 30, 15, 15F, 1).setMultiShot(5, 3));
		this.goalSelector.addGoal(4, new DemonAttackGoal(this, 1.0D, false, 2));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new ProwlerEntity.FindPlayerGoal(this, this::isAngryAt));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, false));
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
		private int statecheck;

		private AbstractRangedAttack attack;

		public RangedStrafeAttackGoal(ProwlerEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn,
				int attackCooldownIn, int visibleTicksDelay, int strafeTicks, float maxAttackDistanceIn, int state) {
			this.entity = mob;
			this.moveSpeedAmp = moveSpeedAmpIn;
			this.attackCooldown = attackCooldownIn;
			this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
			this.attack = attack;
			this.visibleTicksDelay = visibleTicksDelay;
			this.strafeTicks = strafeTicks;
			this.statecheck = state;
		}

		// use defaults
		public RangedStrafeAttackGoal(ProwlerEntity mob, AbstractRangedAttack attack, int attackCooldownIn) {
			this.entity = mob;
			this.attackCooldown = attackCooldownIn;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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

		public void setAttackCooldown(int attackCooldownIn) {
			this.attackCooldown = attackCooldownIn;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean canUse() {
			return this.entity.getTarget() != null;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean canContinueToUse() {
			return (this.canUse() || !this.entity.getNavigation().isDone());
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void start() {
			super.start();
			this.entity.setAggressive(true);
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		public void stop() {
			super.stop();
			this.entity.setAggressive(false);
			this.entity.setAttackingState(0);
			this.seeTime = 0;
			this.attackTime = -1;
			this.entity.stopUsingItem();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				double distanceToTargetSq = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(),
						livingentity.getZ());
				boolean inLineOfSight = this.entity.getSensing().canSee(livingentity);
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
					this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
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

					this.entity.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F,
							this.strafingClockwise ? 0.5F : -0.5F);
					this.entity.lookAt(livingentity, 30.0F, 30.0F);
				} else {
					this.entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
				}

				// attack
				if (multiShooting) {
					if (tickMultiShot())
						this.attack.shoot();
					this.entity.teleport();
					return;
				}

				if (this.seeTime >= this.visibleTicksDelay) {
					if (this.attackTime >= this.attackCooldown) {
						this.attack.shoot();
						this.entity.teleport();
						this.attackTime = 0;
					} else
						this.attackTime++;
				}
				this.entity.setAttackingState(attackTime >= attackCooldown * 0.25 ? this.statecheck : 0);
			}
		}
	}

	static class FindPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		private final ProwlerEntity enderman;
		private PlayerEntity pendingTarget;
		private int aggroTime;
		private int teleportTime;
		private final EntityPredicate startAggroTargetConditions;
		private final EntityPredicate continueAggroTargetConditions = (new EntityPredicate()).allowUnseeable();

		public FindPlayerGoal(ProwlerEntity p_i241912_1_, @Nullable Predicate<LivingEntity> p_i241912_2_) {
			super(p_i241912_1_, PlayerEntity.class, 10, false, false, p_i241912_2_);
			this.enderman = p_i241912_1_;
			this.startAggroTargetConditions = (new EntityPredicate()).range(this.getFollowDistance())
					.selector((p_220790_1_) -> {
						return p_i241912_1_.isLookingAtMe((PlayerEntity) p_220790_1_);
					});
		}

		public boolean canUse() {
			this.pendingTarget = this.enderman.level.getNearestPlayer(this.startAggroTargetConditions, this.enderman);
			return this.pendingTarget != null;
		}

		public void start() {
			this.aggroTime = 5;
			this.teleportTime = 0;
		}

		public void stop() {
			this.pendingTarget = null;
			super.stop();
		}

		public boolean canContinueToUse() {
			if (this.pendingTarget != null) {
				this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
				return true;
			} else {
				return this.target != null && this.continueAggroTargetConditions.test(this.enderman, this.target) ? true
						: super.canContinueToUse();
			}
		}

		public void tick() {
			if (this.enderman.getTarget() == null) {
				super.setTarget((LivingEntity) null);
			}

			if (this.pendingTarget != null) {
				if (--this.aggroTime <= 0) {
					this.target = this.pendingTarget;
					this.pendingTarget = null;
					super.start();
				}
			} else {
				if (this.target != null && !this.enderman.isPassenger()) {
					if (this.target.distanceToSqr(this.enderman) > 256.0D && this.teleportTime++ >= 30
							&& this.enderman.teleportTowards(this.target)) {
						if (this.target.distanceToSqr(this.enderman) < 16.0D) {
							this.enderman.teleport();
						}
						this.teleportTime = 0;
					} else if (this.target.distanceToSqr(this.enderman) > 256.0D && this.teleportTime++ >= 30
							&& this.enderman.teleportTowards(this.target)) {
						this.teleportTime = 0;
					}
				}

				super.tick();
			}

		}
	}

	private boolean isLookingAtMe(PlayerEntity p_70821_1_) {
		Vector3d vector3d = p_70821_1_.getViewVector(1.0F).normalize();
		Vector3d vector3d1 = new Vector3d(this.getX() - p_70821_1_.getX(), this.getEyeY() - p_70821_1_.getEyeY(),
				this.getZ() - p_70821_1_.getZ());
		double d0 = vector3d1.length();
		vector3d1 = vector3d1.normalize();
		double d1 = vector3d.dot(vector3d1);
		return d1 > 1.0D - 0.025D / d0 ? p_70821_1_.canSee(this) : false;
	}

	@Override
	public void aiStep() {
		if (this.level.isClientSide) {
			for (int i = 0; i < 2; ++i) {
				this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D,
						this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
						(this.random.nextDouble() - 0.5D) * 2.0D);
			}
		}

		this.jumping = false;
		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerWorld) this.level, true);
		}

		super.aiStep();
	}

	@Override
	protected void customServerAiStep() {
		if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
			float f = this.getBrightness();
			if (f > 0.5F && this.level.canSeeSky(this.blockPosition())
					&& this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleport();
			}
		}

		super.customServerAiStep();
	}

	protected boolean teleport() {
		if (!this.level.isClientSide() && this.isAlive()) {
			double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 16.0D;
			double d1 = this.getY() + (double) (this.random.nextInt(64) - 16);
			double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 16.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleportTowards(Entity p_70816_1_) {
		Vector3d vector3d = new Vector3d(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(),
				this.getZ() - p_70816_1_.getZ());
		vector3d = vector3d.normalize();
		double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.x * 16.0D;
		double d2 = this.getY() + (double) (this.random.nextInt(16) - 8) - vector3d.y * 16.0D;
		double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.z * 16.0D;
		return this.teleport(d1, d2, d3);
	}

	@SuppressWarnings("deprecation")
	private boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(p_70825_1_, p_70825_3_, p_70825_5_);
		while (blockpos$mutable.getY() > 0
				&& !this.level.getBlockState(blockpos$mutable).getMaterial().blocksMotion()) {
			blockpos$mutable.move(Direction.DOWN);
		}

		BlockState blockstate = this.level.getBlockState(blockpos$mutable);
		boolean flag = blockstate.getMaterial().blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(
					this, p_70825_1_, p_70825_3_, p_70825_5_, 0);
			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
				return false;
			boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			return flag2;
		} else {
			return false;
		}
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, config.prowler_health.get())
				.add(Attributes.ATTACK_DAMAGE, config.prowler_melee_damage.get()).add(Attributes.MOVEMENT_SPEED, 0.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

}