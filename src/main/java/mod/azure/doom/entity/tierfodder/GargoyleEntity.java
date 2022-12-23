package mod.azure.doom.entity.tierfodder;

import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GargoyleEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public GargoyleEntity(EntityType<GargoyleEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.moveControl = new GargoyleMoveControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if ((this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (!this.isAttacking() && event.isMoving() && !(this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (this.isAttacking() && event.isMoving() && !(this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("flying"));
			if (!event.isCurrentAnimation(RawAnimation.begin().thenLoop("flying")) && !event.isCurrentAnimation(RawAnimation.begin().thenLoop("walking")))
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			return PlayState.CONTINUE;
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().then("attacking", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 40) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
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
		this.goalSelector.add(4,
				new RangedAttackGoal(this,
						new FireballAttack(this, false).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(DoomConfig.gargoyle_ranged_damage).setSound(SoundEvents.ENTITY_BLAZE_SHOOT,
										1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F),
						1.1D));
		this.goalSelector.add(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public void travel(Vec3d movementInput) {
		if (this.isAttacking()) {
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
		} else {
			super.travel(movementInput);
		}
	}

	static class GargoyleMoveControl extends MoveControl {
		private final GargoyleEntity entity;
		private int courseChangeCooldown;

		public GargoyleMoveControl(GargoyleEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public void tick() {
			if (entity.isAttacking()) {
				if (this.state == MoveControl.State.MOVE_TO) {
					if (this.courseChangeCooldown-- <= 0) {
						this.courseChangeCooldown += this.entity.getRandom().nextInt(5) + 2;
						Vec3d vector3d = new Vec3d(this.targetX - this.entity.getX(), this.targetY - this.entity.getY(),
								this.targetZ - this.entity.getZ());
						double d0 = vector3d.length();
						vector3d = vector3d.normalize();
						if (this.canReach(vector3d, MathHelper.ceil(d0))) {
							this.entity.setVelocity(this.entity.getVelocity().add(vector3d.multiply(0.1D)));
						} else {
							this.state = MoveControl.State.WAIT;
						}
					}
				} else {
					this.state = MoveControl.State.WAIT;
					this.entity.setForwardSpeed(0.0F);
				}
			} else {
				if (this.state == MoveControl.State.MOVE_TO) {
					this.state = MoveControl.State.WAIT;
					double d0 = this.targetX - this.entity.getX();
					double d1 = this.targetY - this.entity.getZ();
					double d2 = this.targetZ - this.entity.getY();
					double d3 = d0 * d0 + d2 * d2 + d1 * d1;
					if (d3 < (double) 2.5000003E-7F) {
						this.entity.setForwardSpeed(0.0F);
						return;
					}
					float f9 = (float) (Math.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), f9, 90.0F));
					this.entity.setMovementSpeed((float) (0.25D));
					BlockPos blockpos = this.entity.getBlockPos();
					BlockState blockstate = this.entity.world.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate.getCollisionShape(this.entity.world, blockpos);
					if (d2 > (double) this.entity.stepHeight
							&& d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.entity.getWidth())
							|| !voxelshape.isEmpty()
									&& this.entity.getY() < voxelshape.getMax(Direction.Axis.Y)
											+ (double) blockpos.getY()
									&& !blockstate.isIn(BlockTags.DOORS) && !blockstate.isIn(BlockTags.FENCES)) {
						this.state = MoveControl.State.JUMPING;
					}
				} else if (this.state == MoveControl.State.JUMPING) {
					this.entity.setMovementSpeed((float) (0.25D));
					if (this.entity.isOnGround()) {
						this.state = MoveControl.State.WAIT;
					}
				} else {
					this.state = MoveControl.State.WAIT;
					this.entity.setForwardSpeed(0.0F);
				}
			}
		}

		private boolean canReach(Vec3d direction, int steps) {
			Box box = this.entity.getBoundingBox();
			for (int i = 1; i < steps; ++i) {
				box = box.offset(direction);
				if (!this.entity.world.isSpaceEmpty(this.entity, box)) {
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
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.gargoyle_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, DoomConfig.gargoyle_melee_damage)
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
		return DoomSounds.GARGOLYE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.GARGOLYE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.GARGOLYE_DEATH;
	}
}