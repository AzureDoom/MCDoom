package mod.azure.doom.entity.tierheavy;

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
import mod.azure.doom.entity.ai.goal.DemonFlightMoveControl;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BloodMaykrEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public BloodMaykrEntity(EntityType<BloodMaykrEntity> type, Level worldIn) {
		super(type, worldIn);
		moveControl = new DemonFlightMoveControl(this, 90, false);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking_weapon", LoopType.PLAY_ONCE));
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
		if (deathTime == 30) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.bloodmaykr_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(4, new RangedStrafeAttackGoal(this, new BloodMaykrEntity.FireballAttack(this).setProjectileOriginOffset(0.8, 0.5, 0.8).setDamage(DoomConfig.bloodmaykr_ranged_damage), 1.0D, 10, 30, 15, 15F, 1));
		goalSelector.addGoal(7, new BloodMaykrEntity.LookAroundGoal(this));
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying())) {
			setGlowingTag(true);
		} else {
			setGlowingTag(false);
		}
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

	/**
	 * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or for AI reasons)
	 */
	@Override
	public boolean onClimbable() {
		return false;
	}

	static class LookAroundGoal extends Goal {
		private final BloodMaykrEntity parentEntity;

		public LookAroundGoal(BloodMaykrEntity ghast) {
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

	static class MoveHelperController extends MoveControl {
		private final BloodMaykrEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(BloodMaykrEntity ghast) {
			super(ghast);
			parentEntity = ghast;
		}

		@Override
		public void tick() {
			if (operation == MoveControl.Operation.MOVE_TO) {
				if (courseChangeCooldown-- <= 0) {
					courseChangeCooldown += parentEntity.getRandom().nextInt(5) + 2;
					Vec3 vector3d = new Vec3(wantedX - parentEntity.getX(), wantedY - parentEntity.getY(), wantedZ - parentEntity.getZ());
					final double d0 = vector3d.length();
					vector3d = vector3d.normalize();
					if (canReach(vector3d, Mth.ceil(d0))) {
						parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
					} else {
						operation = MoveControl.Operation.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
			AABB axisalignedbb = parentEntity.getBoundingBox();

			for (int i = 1; i < p_220673_2_; ++i) {
				axisalignedbb = axisalignedbb.move(p_220673_1_);
				if (!parentEntity.level.noCollision(parentEntity, axisalignedbb)) {
					return false;
				}
			}

			return true;
		}
	}

	public class FireballAttack extends AbstractRangedAttack {

		public FireballAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction, double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(DemonEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(DoomSounds.UNMAKYR_FIRE, 0.7F, 1);
		}

		@Override
		public Projectile getProjectile(Level world, double d2, double d3, double d4) {
			return new BloodBoltEntity(world, parentEntity, d2, d3, d4, damage);
		}
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 3;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MAKYR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MAKYR_DEATH;
	}

	@Override
	public void travel(Vec3 movementInput) {
		if (isInWater()) {
			moveRelative(0.02F, movementInput);
			move(MoverType.SELF, getDeltaMovement());
			this.setDeltaMovement(getDeltaMovement().scale(0.8F));
		} else if (isInLava()) {
			moveRelative(0.02F, movementInput);
			move(MoverType.SELF, getDeltaMovement());
			this.setDeltaMovement(getDeltaMovement().scale(0.5D));
		} else {
			final BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
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
		calculateEntityAnimation(this, false);
	}

}