package mod.azure.doom.entity.tierfodder;

import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PossessedSoldierEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(PossessedSoldierEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public int flameTimer;

	public PossessedSoldierEntity(EntityType<PossessedSoldierEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.moveControl = new SoldierMoveControl(this);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", EDefaultLoopTypes.PLAY_ONCE));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.velocityModified) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (!this.isOnGround() && !this.onGround && this.getVariant() == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<PossessedSoldierEntity> controller = new AnimationController<PossessedSoldierEntity>(this,
				"controller", 0, this::predicate);
		AnimationController<PossessedSoldierEntity> controller1 = new AnimationController<PossessedSoldierEntity>(this,
				"controller1", 0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
						SoundCategory.HOSTILE, 0.1F, 1.0F, true);
			}
		}
		if (event.sound.matches("attack")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PISTOL_HIT,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
	}

	@Override
	public int tickTimer() {
		return age;
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
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4,
				new RangedStrafeAttackGoal(this,
						new PossessedSoldierEntity.FireballAttack(this).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(DoomConfig.possessed_soldier_ranged_damage),
						1.0D, 5, 30, 15, 15F, 1));
		if (this.getVariant() == 2) {
			this.goalSelector.add(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		}
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
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
			return new AttackSound(DoomSounds.PLASMA_FIRING, 1, 1);
		}

		@Override
		public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
			return new BarenBlastEntity(world, this.parentEntity, d2, d3, d4, damage);

		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.possessed_soldier_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1.74F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.PSOLDIER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.PSOLDIER_DEATH;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 3);
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	public int getVariants() {
		return 3;
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 4);
		this.setVariant(var);
		return entityData;
	}

	@Override
	public int getArmor() {
		return this.getVariant() == 3 ? 3 : 0;
	}

	public void travel(Vec3d movementInput) {
		if (this.isAttacking() && this.getVariant() == 2) {
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

	static class SoldierMoveControl extends MoveControl {
		private final PossessedSoldierEntity entity;
		private int courseChangeCooldown;

		public SoldierMoveControl(PossessedSoldierEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public void tick() {
			if (entity.isAttacking() && this.entity.getVariant() == 2) {
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

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 2;
	}

	public int getFlameTimer() {
		return flameTimer;
	}
}