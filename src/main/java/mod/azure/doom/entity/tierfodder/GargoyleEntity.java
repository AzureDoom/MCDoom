package mod.azure.doom.entity.tierfodder;

import java.util.EnumSet;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.ai.goal.GargoyleFlightMoveControl;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GargoyleEntity extends DemonEntity implements IAnimatable {

	public GargoyleEntity(EntityType<GargoyleEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.moveControl = new GargoyleFlightMoveControl(this, 90, false);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!this.isOnGround() && this.onGround && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", true));
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
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GargoyleEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<GargoyleEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 50) {
			this.remove();
			if (world.isClient) {
			}
		}
	}

	public static boolean spawning(EntityType<GargoyleEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(7, new GargoyleEntity.LookAtTargetGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.goalSelector.add(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.goalSelector.add(4,
				new RangedStrafeAttackGoal(this,
						new FireballAttack(this, false).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(config.gargoyle_ranged_damage).setSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F,
										1.4F + this.getRandom().nextFloat() * 0.35F),
						1.0D, 50, 30, 15, 15F, 1).setMultiShot(3, 3));
		this.goalSelector.add(4, new DemonAttackGoal(this, 1.0D, false, 2));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	static class LookAtTargetGoal extends Goal {
		private final GargoyleEntity ghast;

		public LookAtTargetGoal(GargoyleEntity ghast) {
			this.ghast = ghast;
			this.setControls(EnumSet.of(Goal.Control.LOOK));
		}

		public boolean canStart() {
			return true;
		}

		public void tick() {
			if (this.ghast.getTarget() == null) {
				Vec3d vec3d = this.ghast.getVelocity();
				this.ghast.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
				this.ghast.bodyYaw = this.ghast.yaw;
			} else {
				LivingEntity livingEntity = this.ghast.getTarget();
				if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0D) {
					double e = livingEntity.getX() - this.ghast.getX();
					double f = livingEntity.getZ() - this.ghast.getZ();
					this.ghast.yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
					this.ghast.bodyYaw = this.ghast.yaw;
				}
			}

		}
	}

	static class GhastMoveControl extends MoveControl {
		private final GargoyleEntity ghast;
		private int collisionCheckCooldown;

		public GhastMoveControl(GargoyleEntity ghast) {
			super(ghast);
			this.ghast = ghast;
		}

		public void tick() {
			if (this.state == MoveControl.State.MOVE_TO) {
				if (this.collisionCheckCooldown-- <= 0) {
					this.collisionCheckCooldown += this.ghast.getRandom().nextInt(5) + 2;
					Vec3d vec3d = new Vec3d(this.targetX - this.ghast.getX(), this.targetY - this.ghast.getY(),
							this.targetZ - this.ghast.getZ());
					double d = vec3d.length();
					vec3d = vec3d.normalize();
					if (this.willCollide(vec3d, MathHelper.ceil(d))) {
						this.ghast.setVelocity(this.ghast.getVelocity().add(vec3d.multiply(0.1D)));
					} else {
						this.state = MoveControl.State.WAIT;
					}
				}

			}
		}

		private boolean willCollide(Vec3d direction, int steps) {
			Box box = this.ghast.getBoundingBox();

			for (int i = 1; i < steps; ++i) {
				box = box.offset(direction);
				if (!this.ghast.world.isSpaceEmpty(this.ghast, box)) {
					return false;
				}
			}

			return true;
		}
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

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.gargoyle_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.gargoyle_melee_damage)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_FLYING_SPEED, 2.25D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.GARGOLYE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.GARGOLYE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.GARGOLYE_DEATH;
	}
}