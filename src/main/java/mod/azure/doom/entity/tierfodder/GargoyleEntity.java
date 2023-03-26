package mod.azure.doom.entity.tierfodder;

import java.util.EnumSet;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
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

public class GargoyleEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public GargoyleEntity(EntityType<GargoyleEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		moveControl = new GargoyleMoveControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (!isAggressive() && event.isMoving() && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (isAggressive() && event.isMoving() && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("flying"));
			if (!event.isCurrentAnimation(RawAnimation.begin().thenLoop("flying")) && !event.isCurrentAnimation(RawAnimation.begin().thenLoop("walking")))
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			return PlayState.CONTINUE;
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 50) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		goalSelector.addGoal(4, new RangedAttackGoal(this, new FireballAttack(this, false).setProjectileOriginOffset(0.8, 0.8, 0.8).setDamage(DoomConfig.gargoyle_ranged_damage).setSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.4F + getRandom().nextFloat() * 0.35F), 1.1D));
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		goalSelector.addGoal(7, new GargoyleEntity.LookAroundGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		final FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void travel(Vec3 movementInput) {
		if (isAggressive()) {
			if (isInWater()) {
				moveRelative(0.02F, movementInput);
				move(MoverType.SELF, getDeltaMovement());
				this.setDeltaMovement(getDeltaMovement().scale(0.8F));
			} else if (isInLava()) {
				moveRelative(0.02F, movementInput);
				move(MoverType.SELF, getDeltaMovement());
				this.setDeltaMovement(getDeltaMovement().scale(0.5D));
			} else {
				final BlockPos ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
				float f = 0.91F;
				if (onGround) {
					f = level.getBlockState(ground).getBlock().getFriction() * 0.91F;
				}
				final float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (onGround) {
					f = level.getBlockState(ground).getBlock().getFriction() * 0.91F;
				}
				moveRelative(onGround ? 0.1F * f1 : 0.02F, movementInput);
				move(MoverType.SELF, getDeltaMovement());
				this.setDeltaMovement(getDeltaMovement().scale(f));
			}
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

		@Override
		public void tick() {
			if (entity.isAggressive()) {
				if (operation == MoveControl.Operation.MOVE_TO) {
					if (courseChangeCooldown-- <= 0) {
						courseChangeCooldown += entity.getRandom().nextInt(5) + 2;
						Vec3 vector3d = new Vec3(wantedX - entity.getX(), wantedY - entity.getY(), wantedZ - entity.getZ());
						final double d0 = vector3d.length();
						vector3d = vector3d.normalize();
						if (canReach(vector3d, Mth.ceil(d0))) {
							entity.setDeltaMovement(entity.getDeltaMovement().add(vector3d.scale(0.1D)));
						} else {
							operation = MoveControl.Operation.WAIT;
						}
					}
				} else {
					operation = MoveControl.Operation.WAIT;
					entity.setZza(0.0F);
				}
			} else if (operation == MoveControl.Operation.MOVE_TO) {
				operation = MoveControl.Operation.WAIT;
				final double d0 = wantedX - entity.getX();
				final double d1 = wantedZ - entity.getZ();
				final double d2 = wantedY - entity.getY();
				final double d3 = d0 * d0 + d2 * d2 + d1 * d1;
				if (d3 < 2.5000003E-7F) {
					entity.setZza(0.0F);
					return;
				}
				final float f9 = (float) (Mth.atan2(d1, d0) * (180F / (float) Math.PI)) - 90.0F;
				entity.setYRot(rotlerp(mob.getYRot(), f9, 90.0F));
				entity.setSpeed((float) 0.25D);
				final BlockPos blockpos = mob.blockPosition();
				final BlockState blockstate = mob.level.getBlockState(blockpos);
				final VoxelShape voxelshape = blockstate.getCollisionShape(mob.level, blockpos);
				if (d2 > mob.getEyeHeight() && d0 * d0 + d1 * d1 < Math.max(1.0F, mob.getBbWidth()) || !voxelshape.isEmpty() && mob.getY() < voxelshape.max(Direction.Axis.Y) + blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
					operation = MoveControl.Operation.JUMPING;
				}
			} else if (operation == MoveControl.Operation.JUMPING) {
				mob.setSpeed((float) 0.25D);
				if (mob.isOnGround()) {
					operation = MoveControl.Operation.WAIT;
				}
			} else {
				operation = MoveControl.Operation.WAIT;
				entity.setZza(0.0F);
			}
		}

		private boolean canReach(Vec3 direction, int steps) {
			AABB axisalignedbb = mob.getBoundingBox();
			for (int i = 1; i < steps; ++i) {
				axisalignedbb = axisalignedbb.move(direction);
				if (!mob.level.noCollision(entity, axisalignedbb)) {
					return false;
				}
			}
			return true;
		}
	}

	static class LookAroundGoal extends Goal {
		private final GargoyleEntity parentEntity;

		public LookAroundGoal(GargoyleEntity ghast) {
			parentEntity = ghast;
			setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return true;
		}

		@Override
		public void tick() {
			if (parentEntity.getTarget() == null) {
				final Vec3 vec3d = parentEntity.getDeltaMovement();
				parentEntity.yo = -((float) Mth.atan2(vec3d.x, vec3d.z)) * (180F / (float) Math.PI);
				parentEntity.yBodyRot = parentEntity.getYRot();
			} else {
				final LivingEntity livingentity = parentEntity.getTarget();
				if (livingentity.distanceToSqr(parentEntity) < 4096.0D) {
					final double d1 = livingentity.getX() - parentEntity.getX();
					final double d2 = livingentity.getZ() - parentEntity.getZ();
					parentEntity.yo = -((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI);
					parentEntity.yBodyRot = parentEntity.getYRot();
				}
			}

		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.gargoyle_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
		return DoomSounds.GARGOLYE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.GARGOLYE_DEATH;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

}