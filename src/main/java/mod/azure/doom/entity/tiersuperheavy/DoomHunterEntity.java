package mod.azure.doom.entity.tiersuperheavy;

import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonFlightMoveControl;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DoomHunterEntity extends DemonEntity implements IAnimatable {

	public DoomHunterEntity(EntityType<? extends DoomHunterEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.moveControl = new DemonFlightMoveControl(this, 90, false);
	}

	private AnimationFactory factory = new AnimationFactory(this);
	public int flameTimer;

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!this.isOnGround() && this.onGround && this.getHealth() > (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (!this.isOnGround() && this.onGround && this.getHealth() <= (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_nosled", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.getHealth() <= (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_nosled", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
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
		if (this.dataTracker.get(STATE) == 4 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flamethrower_nosled", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 5 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("chainsaw_nosled", true));
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
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(4, new DoomHunterEntity.AttackGoal(this));
		this.goalSelector.add(4, new DoomHunterEntity.DemonAttackGoal(this, 1.0D, false));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge());
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, MerchantEntity.class, true));
	}

	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	@Override
	protected void updateGoalControls() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlEnabled(Goal.Control.LOOK, flag);
		super.updateGoalControls();
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
			this.cooldown = 0;
			this.parentEntity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAttacking(false);
			this.parentEntity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingEntity = this.parentEntity.getTarget();
			if (livingEntity.squaredDistanceTo(this.parentEntity) < 4096.0D && this.parentEntity.canSee(livingEntity)) {
				World world = this.parentEntity.world;
				Vec3d vec3d = this.parentEntity.getRotationVec(1.0F);
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
				int j;
				if (this.cooldown == 15) {
					if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
						for (j = 0; j < 16; ++j) {
							double l1 = 1.25D * (double) (j + 1);
							int m = 1 * j;
							parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(f2) * l1,
									parentEntity.getZ() + (double) MathHelper.sin(f2) * l1 + 0.5, d, e1, f2, m);
							if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
								this.parentEntity.setAttackingState(4);
							} else {
								this.parentEntity.setAttackingState(2);
							}
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
		private final DoomHunterEntity actor;
		private int ticks;

		public DemonAttackGoal(DoomHunterEntity zombie, double speed, boolean pauseWhenMobIdle) {
			super(zombie, speed, pauseWhenMobIdle);
			this.actor = zombie;
		}

		public void start() {
			super.start();
			this.ticks = 0;
		}

		public void stop() {
			super.stop();
			this.actor.setAttacking(false);
			this.actor.setAttackingState(0);
		}

		@Override
		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void tick() {
			super.tick();
			++this.ticks;
			LivingEntity livingEntity = this.actor.getTarget();
			if (livingEntity != null) {
				this.actor.getLookControl().lookAt(livingEntity, 90.0F, 30.0F);
				double d0 = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
				if (livingEntity.squaredDistanceTo(this.actor) < 4.0D) {
					if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
						this.actor.setAttacking(true);
					}
				}
				this.attack(livingEntity, d0);
			}
		}

		@Override
		protected void attack(LivingEntity livingentity, double squaredDistance) {
			double d0 = this.getSquaredMaxAttackDistance(livingentity);
			if (squaredDistance <= d0 && this.getCooldown() <= 0) {
				this.resetCooldown();
				this.actor.setAttackingState(actor.getHealth() < (actor.getMaxHealth() * 0.50) ? 5 : 3);
				this.actor.tryAttack(livingentity);
			}
		}

		@Override
		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			return (double) (this.mob.getWidth() * 1.0F * this.mob.getWidth() * 1.0F + entity.getWidth());
		}

	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.doomhunter_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.doomhunter_melee_damage)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 2.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 50D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		return entityData;
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
	public void tickMovement() {
		super.tickMovement();
		flameTimer = (flameTimer + 1) % 8;
		if (this.getHealth() < 75.0D) {
			if (!this.world.isClient) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10000000, 2));
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10000000, 1));
			}
		}
	}

	public int getFlameTimer() {
		return flameTimer;
	}

}