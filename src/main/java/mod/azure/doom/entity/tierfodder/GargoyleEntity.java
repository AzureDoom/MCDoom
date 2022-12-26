package mod.azure.doom.entity.tierfodder;

import java.util.EnumSet;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
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

	public GargoyleEntity(EntityType<GargoyleEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.moveControl = new GargoyleMoveControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (!this.isAggressive() && event.isMoving()
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (this.isAggressive() && event.isMoving()
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("flying"));
			if (!event.isCurrentAnimation(RawAnimation.begin().thenLoop("flying"))
					&& !event.isCurrentAnimation(RawAnimation.begin().thenLoop("walking")))
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			return PlayState.CONTINUE;
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 50) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4,
				new RangedAttackGoal(this,
						new FireballAttack(this, false).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(DoomConfig.SERVER.gargoyle_ranged_damage.get().floatValue()).setSound(SoundEvents.BLAZE_SHOOT, 1.0F,
										1.4F + this.getRandom().nextFloat() * 0.35F),
						1.1D));
		this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.goalSelector.addGoal(7, new GargoyleEntity.LookAroundGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	protected PathNavigation createNavigation(Level worldIn) {
		FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public void travel(Vec3 movementInput) {
		if (this.isAggressive()) {
			if (this.isInWater()) {
				this.moveRelative(0.02F, movementInput);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
			} else if (this.isInLava()) {
				this.moveRelative(0.02F, movementInput);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
			} else {
				BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
				float f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getBlock().getFriction() * 0.91F;
				}
				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getBlock().getFriction() * 0.91F;
				}
				this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, movementInput);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
			}
			this.calculateEntityAnimation(this, false);
		} else {
			super.travel(movementInput);
		}
	}

	static class GargoyleMoveControl extends MoveControl {
		protected final DemonEntity entity;
		private int courseChangeCooldown;

		public GargoyleMoveControl(DemonEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public void tick() {
			if (entity.isAggressive()) {
				if (this.operation == MoveControl.Operation.MOVE_TO) {
					if (this.courseChangeCooldown-- <= 0) {
						this.courseChangeCooldown += this.entity.getRandom().nextInt(5) + 2;
						Vec3 vector3d = new Vec3(this.wantedX - this.entity.getX(), this.wantedY - this.entity.getY(),
								this.wantedZ - this.entity.getZ());
						double d0 = vector3d.length();
						vector3d = vector3d.normalize();
						if (this.canReach(vector3d, Mth.ceil(d0))) {
							this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(vector3d.scale(0.1D)));
						} else {
							this.operation = MoveControl.Operation.WAIT;
						}
					}
				} else {
					this.operation = MoveControl.Operation.WAIT;
					this.entity.setZza(0.0F);
				}
			} else {
				if (this.operation == MoveControl.Operation.MOVE_TO) {
					this.operation = MoveControl.Operation.WAIT;
					double d0 = this.wantedX - this.entity.getX();
					double d1 = this.wantedZ - this.entity.getZ();
					double d2 = this.wantedY - this.entity.getY();
					double d3 = d0 * d0 + d2 * d2 + d1 * d1;
					if (d3 < (double) 2.5000003E-7F) {
						this.entity.setZza(0.0F);
						return;
					}
					float f9 = (float) (Mth.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.entity.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
					this.entity.setSpeed((float) (0.25D));
					BlockPos blockpos = this.mob.blockPosition();
					BlockState blockstate = this.mob.level.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level, blockpos);
					if (d2 > (double) this.mob.getEyeHeight()
							&& d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth())
							|| !voxelshape.isEmpty()
									&& this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY()
									&& !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
						this.operation = MoveControl.Operation.JUMPING;
					}
				} else if (this.operation == MoveControl.Operation.JUMPING) {
					this.mob.setSpeed((float) (0.25D));
					if (this.mob.isOnGround()) {
						this.operation = MoveControl.Operation.WAIT;
					}
				} else {
					this.operation = MoveControl.Operation.WAIT;
					this.entity.setZza(0.0F);
				}
			}
		}

		private boolean canReach(Vec3 direction, int steps) {
			AABB axisalignedbb = this.mob.getBoundingBox();
			for (int i = 1; i < steps; ++i) {
				axisalignedbb = axisalignedbb.move(direction);
				if (!this.mob.level.noCollision(this.entity, axisalignedbb)) {
					return false;
				}
			}
			return true;
		}
	}

	static class LookAroundGoal extends Goal {
		private final GargoyleEntity parentEntity;

		public LookAroundGoal(GargoyleEntity ghast) {
			this.parentEntity = ghast;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.parentEntity.getTarget() == null) {
				Vec3 vec3d = this.parentEntity.getDeltaMovement();
				this.parentEntity.yo = -((float) Mth.atan2(vec3d.x, vec3d.z)) * (180F / (float) Math.PI);
				this.parentEntity.yBodyRot = this.parentEntity.getYRot();
			} else {
				LivingEntity livingentity = this.parentEntity.getTarget();
				if (livingentity.distanceToSqr(this.parentEntity) < 4096.0D) {
					double d1 = livingentity.getX() - this.parentEntity.getX();
					double d2 = livingentity.getZ() - this.parentEntity.getZ();
					this.parentEntity.yo = -((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.parentEntity.yBodyRot = this.parentEntity.getYRot();
				}
			}

		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.gargoyle_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.GARGOLYE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.GARGOLYE_DEATH.get();
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

}