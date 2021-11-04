package mod.azure.doom.entity.tiersuperheavy;

import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonFlightMoveControl;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
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

public class DoomHunterEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public DoomHunterEntity(EntityType<? extends DoomHunterEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.moveControl = new DemonFlightMoveControl(this, 90, false);
	}

	private AnimationFactory factory = new AnimationFactory(this);
	public int flameTimer;

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("rockets", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flamethrower", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("chainsaw", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<DoomHunterEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<DoomHunterEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public static boolean spawning(EntityType<BaronEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(4, new DoomHunterEntity.AttackGoal(this));
		this.goalSelector.add(4, new DemonAttackGoal(this, 1.0D, false));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	@Override
	protected void updateGoalControls() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlEnabled(Goal.Control.LOOK, flag);
		super.updateGoalControls();
	}

	protected EntityNavigation createNavigation(World worldIn) {
		BirdNavigation flyingpathnavigator = new BirdNavigation(this, worldIn);
		flyingpathnavigator.setCanPathThroughDoors(false);
		flyingpathnavigator.setCanSwim(true);
		flyingpathnavigator.setCanEnterOpenDoors(true);
		return flyingpathnavigator;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	public boolean isClimbing() {
		return true;
	}

	static class AttackGoal extends Goal {
		private final DoomHunterEntity parentEntity;
		protected int cooldown = 0;

		public AttackGoal(DoomHunterEntity parentEntity) {
			this.parentEntity = parentEntity;
		}

		public boolean canStart() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			super.start();
			this.parentEntity.setAttacking(true);
			this.cooldown = -1;
			this.parentEntity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAttacking(false);
			this.parentEntity.setAttackingState(0);
			this.cooldown = -1;
		}

		public void tick() {
			LivingEntity livingEntity = this.parentEntity.getTarget();
			if (this.parentEntity.canSee(livingEntity)) {
				Vec3d vec3d = livingEntity.getRotationVec(1.0F);
				World world = this.parentEntity.world;
				++this.cooldown;
				double f = livingEntity.getX() - (this.parentEntity.getX() + vec3d.x * 2.0D);
				double g = livingEntity.getBodyY(0.5D) - (0.5D + this.parentEntity.getBodyY(0.5D));
				double h = livingEntity.getZ() - (this.parentEntity.getZ() + vec3d.z * 2.0D);
				RocketMobEntity fireballEntity = new RocketMobEntity(world, this.parentEntity, f, g, h,
						config.doomhunter_ranged_damage);
				double d = Math.min(livingEntity.getY(), parentEntity.getY());
				double e1 = Math.max(livingEntity.getY(), parentEntity.getY()) + 1.0D;
				float f2 = (float) MathHelper.atan2(livingEntity.getZ() - parentEntity.getZ(),
						livingEntity.getX() - parentEntity.getX());
				if (this.cooldown == 15) {
					if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
						for (int l = 0; l < 16; ++l) {
							double l1 = 1.25D * (double) (l + 1);
							int m = 1 * l;
							parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(f2) * l1,
									parentEntity.getZ() + (double) MathHelper.sin(f2) * l1 + 0.5, d, e1, f2, m);
							this.parentEntity.setAttackingState(2);
						}
					}
					if (parentEntity.getHealth() > (parentEntity.getMaxHealth() * 0.50)) {
						this.parentEntity.setAttackingState(1);
						fireballEntity.updatePosition(this.parentEntity.getX() + vec3d.x * 2.0D,
								this.parentEntity.getBodyY(0.5D) + 0.5D, parentEntity.getZ() + vec3d.z * 2.0D);
						world.spawnEntity(fireballEntity);
					}
				}
				if (this.cooldown == 25) {
					this.parentEntity.setAttackingState(0);
					this.cooldown = -150;
				}
			} else if (this.cooldown > 0) {
				--this.cooldown;
			}
			this.parentEntity.lookAtEntity(livingEntity, 30.0F, 30.0F);
		}
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockPos = new BlockPos(x, y, z);
		boolean bl = false;
		double d = -0.75D;
		do {
			BlockPos blockPos2 = blockPos.down();
			BlockState blockState = this.world.getBlockState(blockPos2);
			if (blockState.isSideSolidFullSquare(this.world, blockPos2, Direction.UP)) {
				if (!this.world.isAir(blockPos)) {
					BlockState blockState2 = this.world.getBlockState(blockPos);
					VoxelShape voxelShape = blockState2.getCollisionShape(this.world, blockPos);
					if (!voxelShape.isEmpty()) {
						d = voxelShape.getMax(Direction.Axis.Y);
					}
				}
				bl = true;
				break;
			}
			blockPos = blockPos.down();
		} while (blockPos.getY() >= MathHelper.floor(maxY) - 1);

		if (bl) {
			DoomFireEntity fang = new DoomFireEntity(this.world, x, (double) blockPos.getY() + d, z, yaw, warmup, this,
					config.doomhunter_ranged_damage);
			fang.setFireTicks(age);
			fang.isInvisible();
			this.world.spawnEntity(fang);
		}
	}

	public class DemonAttackGoal extends MeleeAttackGoal {
		private final DoomHunterEntity entity;
		private final double speedModifier;
		private int ticksUntilNextAttack;
		private int ticksUntilNextPathRecalculation;
		private final boolean followingTargetEvenIfNotSeen;
		private double pathedTargetX;
		private double pathedTargetY;
		private double pathedTargetZ;

		public DemonAttackGoal(DoomHunterEntity zombieIn, double speedIn, boolean longMemoryIn) {
			super(zombieIn, speedIn, longMemoryIn);
			this.entity = zombieIn;
			this.speedModifier = speedIn;
			this.followingTargetEvenIfNotSeen = longMemoryIn;
		}

		public void start() {
			super.start();
		}

		public boolean canStart() {
			return super.canStart();
		}

		public void stop() {
			super.stop();
			this.entity.setAttacking(false);
			this.entity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				this.mob.getLookControl().lookAt(livingentity, 30.0F, 30.0F);
				double d0 = this.mob.squaredDistanceTo(livingentity.getX(), livingentity.getY(), livingentity.getZ());
				this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
				if ((this.followingTargetEvenIfNotSeen || this.mob.getVisibilityCache().canSee(livingentity))
						&& this.ticksUntilNextPathRecalculation <= 0
						&& (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
								|| livingentity.squaredDistanceTo(this.pathedTargetX, this.pathedTargetY,
										this.pathedTargetZ) >= 1.0D
								|| this.mob.getRandom().nextFloat() < 0.05F)) {
					this.pathedTargetX = livingentity.getX();
					this.pathedTargetY = livingentity.getY();
					this.pathedTargetZ = livingentity.getZ();
					this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
					if (d0 > 1024.0D) {
						this.ticksUntilNextPathRecalculation += 10;
					} else if (d0 > 256.0D) {
						this.ticksUntilNextPathRecalculation += 5;
					}

					if (!this.mob.getNavigation().startMovingTo(livingentity, this.speedModifier)) {
						this.ticksUntilNextPathRecalculation += 15;
					}
				}

				this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
				this.attack(livingentity, d0);
			}
		}

		@Override
		protected void attack(LivingEntity livingentity, double squaredDistance) {
			double d0 = this.getSquaredMaxAttackDistance(livingentity);
			if (squaredDistance <= d0 && this.method_28348() <= 0) {
				this.method_28346();
				this.entity.setAttackingState(3);
				this.mob.tryAttack(livingentity);
			}
		}

		@Override
		protected int method_28349() {
			return 50;
		}

		@Override
		protected double getSquaredMaxAttackDistance(LivingEntity attackTarget) {
			return (double) (this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F + attackTarget.getWidth());
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.doomhunter_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.doomhunter_melee_damage)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 2.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 50D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 6.55F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.DOOMHUNTER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.DOOMHUNTER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.DOOMHUNTER_DEATH;
	}

	@Override
	public int getArmor() {
		float health = this.getHealth();
		return (health < (this.getMaxHealth() * 0.90) && health >= (this.getMaxHealth() * 0.85) ? 8
				: health < (this.getMaxHealth() * 0.80) && health >= (this.getMaxHealth() * 0.75) ? 6
						: health < (this.getMaxHealth() * 0.70) && health >= (this.getMaxHealth() * 0.65) ? 4
								: health < (this.getMaxHealth() * 0.60) && health >= (this.getMaxHealth() * 0.55) ? 4
										: health < (this.getMaxHealth() * 0.55)
												&& health >= (this.getMaxHealth() * 0.50) ? 2
														: health < (this.getMaxHealth() * 0.50) ? 0 : 10);
	}

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 8;
		if (this.getHealth() < (this.getMaxHealth() * 0.50)) {
			if (!this.world.isClient) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10000000, 2));
			}
		}
	}

	public int getFlameTimer() {
		return flameTimer;
	}

}