package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
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
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
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

public class BloodMaykrEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);

	public BloodMaykrEntity(EntityType<BloodMaykrEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveControl = new GhastMoveControl(this);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking_weapon", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<BloodMaykrEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<BloodMaykrEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public static boolean spawning(EntityType<BloodMaykrEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(7, new BloodMaykrEntity.LookAtTargetGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4,
				new RangedStrafeAttackGoal(this, new BloodMaykrEntity.FireballAttack(this)
						.setProjectileOriginOffset(0.8, 0.5, 0.8).setDamage(config.bloodmaykr_ranged_damage), 1.0D, 10,
						30, 15, 15F, 1));
		this.goalSelector.add(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	@Override
	public void tick() {
		super.tick();
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			this.setGlowing(true);
		} else {
			this.setGlowing(false);
		}
	}

	static class LookAtTargetGoal extends Goal {
		private final BloodMaykrEntity ghast;

		public LookAtTargetGoal(BloodMaykrEntity ghast) {
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

	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	public void travel(Vec3d movementInput) {
		if (this.isTouchingWater()) {
			this.updateVelocity(0.02F, movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.800000011920929D));
		} else if (this.isInLava()) {
			this.updateVelocity(0.02F, movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.5D));
		} else {
			float f = 0.91F;
			if (this.onGround) {
				f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock()
						.getSlipperiness() * 0.91F;
			}

			float g = 0.16277137F / (f * f * f);
			f = 0.91F;
			if (this.onGround) {
				f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock()
						.getSlipperiness() * 0.91F;
			}

			this.updateVelocity(this.onGround ? 0.1F * g : 0.02F, movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply((double) f));
		}
		this.updateLimbs(this, false);
	}

	static class GhastMoveControl extends MoveControl {
		private final BloodMaykrEntity ghast;
		private int collisionCheckCooldown;

		public GhastMoveControl(BloodMaykrEntity ghast) {
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

	public class FireballAttack extends AbstractRangedAttack {

		public FireballAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
				double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(DemonEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(ModSoundEvents.UNMAKYR_FIRE, 1, 1);
		}

		@Override
		public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
			return new BloodBoltEntity(world, this.parentEntity, d2, d3, d4, damage);
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.bloodmaykr_health)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.MAKYR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.MAKYR_DEATH;
	}

}