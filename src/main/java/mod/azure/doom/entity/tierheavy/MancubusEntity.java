package mod.azure.doom.entity.tierheavy;

import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MancubusEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);

	public MancubusEntity(EntityType<MancubusEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.velocityModified) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("firing", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("ground", false));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {

		AnimationController<MancubusEntity> controller = new AnimationController<MancubusEntity>(this, "controller", 0,
				this::predicate);
		AnimationController<MancubusEntity> controller1 = new AnimationController<MancubusEntity>(this, "controller1",
				0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener1);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener1(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("attack") && this.dataTracker.get(STATE) == 1) {
			this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.ROCKET_FIRING,
					SoundCategory.HOSTILE, 0.25F, 1.0F, true);
		}
		if (event.sound.matches("flames") && this.dataTracker.get(STATE) > 1) {
			this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE,
					SoundCategory.HOSTILE, 0.25F, 1.0F, true);
		}
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (this.world.isClient) {
			if (event.sound.matches("walk")) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
			if (event.sound.matches("talk")) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.MANCUBUS_STEP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
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
		if (this.deathTime == 80) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public static boolean spawning(EntityType<MancubusEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, MerchantEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, IronGolemEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.goalSelector.add(1, new MancubusEntity.ShootFireballGoal(this));
		this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	@Override
	protected void updateGoalControls() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlEnabled(Goal.Control.LOOK, flag);
		super.updateGoalControls();
	}

	static class ShootFireballGoal extends Goal {
		private final MancubusEntity parentEntity;
		protected int cooldown = 0;

		public ShootFireballGoal(MancubusEntity parentEntity) {
			this.parentEntity = parentEntity;
		}

		public boolean canStart() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			super.start();
			this.parentEntity.setAttacking(true);
			this.cooldown = 0;
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
				BarenBlastEntity fireballEntity = new BarenBlastEntity(world, this.parentEntity, f, g, h, 6);
				double d = Math.min(livingEntity.getY(), parentEntity.getY());
				double e1 = Math.max(livingEntity.getY(), parentEntity.getY()) + 1.0D;
				float f2 = (float) MathHelper.atan2(livingEntity.getZ() - parentEntity.getZ(),
						livingEntity.getX() - parentEntity.getX());
				int j;
				if (this.cooldown == 15) {
					if (parentEntity.distanceTo(livingEntity) < 3.0D) {
						for (j = 0; j < 5; ++j) {
							float h2 = f2 + (float) j * (float) Math.PI * 0.4F;
							parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(h2) * 1.5D,
									parentEntity.getZ() + (double) MathHelper.sin(h2) * 1.5D, d, e1, h2, 0);
						}
						this.parentEntity.setAttackingState(3);
					} else if (parentEntity.distanceTo(livingEntity) < 13.0D
							&& parentEntity.distanceTo(livingEntity) > 3.0D) {
						for (j = 0; j < 16; ++j) {
							double l1 = 1.25D * (double) (j + 1);
							int m = 1 * j;
							parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(f2) * l1 + 0.5,
									parentEntity.getZ() + (double) MathHelper.sin(f2) * l1, d, e1, f2, m);
						}
						this.parentEntity.setAttackingState(2);
					} else {
						fireballEntity.updatePosition(this.parentEntity.getX() + vec3d.x * 2.0D,
								this.parentEntity.getBodyY(0.5D) + 0.5D, parentEntity.getZ() + vec3d.z * 2.0D);
						world.spawnEntity(fireballEntity);
						this.parentEntity.setAttackingState(1);
					}
				}
				if (this.cooldown == 20) {
					if (parentEntity.distanceTo(livingEntity) < 3.0D) {
						for (int k = 0; k < 8; ++k) {
							float f3 = f2 + (float) k * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
							parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(f3) * 2.5D,
									parentEntity.getZ() + (double) MathHelper.sin(f3) * 2.5D, d, e1, f3, 3);
						}
					} else if (parentEntity.distanceTo(livingEntity) < 13.0D
							&& parentEntity.distanceTo(livingEntity) > 3.0D) {
						for (j = 0; j < 16; ++j) {
							double l1 = 1.25D * (double) (j + 1);
							int m = 1 * j;
							parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(f2) * l1,
									parentEntity.getZ() + (double) MathHelper.sin(f2) * l1 + 0.5, d, e1, f2, m);
						}
					} else {
						fireballEntity.updatePosition(this.parentEntity.getX() + vec3d.x * 2.0D,
								this.parentEntity.getBodyY(0.5D) + 0.5D, parentEntity.getZ() + vec3d.z * 2.0D);
						world.spawnEntity(fireballEntity);
					}
				}
				if (this.cooldown == 25) {
					this.parentEntity.setAttackingState(0);
					this.cooldown = -15;
				}
			} else if (this.cooldown > 0) {
				this.parentEntity.setAttackingState(0);
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
					DoomConfig.mancubus_ranged_damage);
			fang.setFireTicks(age);
			fang.isInvisible();
			this.world.spawnEntity(fang);
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.mancubus_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, DoomConfig.mancubus_melee_damage)
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
		return 2.80F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MANCUBUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MANCUBUS_DEATH;
	}
}