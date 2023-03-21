package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.SplittableRandom;

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
import mod.azure.doom.entity.ai.goal.RangedStaticAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CacodemonEntity extends DemonEntity implements Enemy, GeoEntity {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CacodemonEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public CacodemonEntity(EntityType<? extends CacodemonEntity> type, Level worldIn) {
		super(type, worldIn);
		moveControl = new CacodemonEntity.GhastMoveControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
		if (deathTime == 30) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setVariant(compound.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());
	}

	public int getVariant() {
		return Mth.clamp(entityData.get(VARIANT), 1, 4);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 4;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final SplittableRandom random = new SplittableRandom();
		final int var = random.nextInt(0, 6);
		setVariant(var);
		return spawnDataIn;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MAX_HEALTH, DoomConfig.cacodemon_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		goalSelector.addGoal(7, new CacodemonEntity.LookAroundGoal(this));
		goalSelector.addGoal(4, new RangedStaticAttackGoal(this, new FireballAttack(this, true).setDamage(DoomConfig.cacodemon_ranged_damage).setProjectileOriginOffset(1.5, 0.3, 1.5).setSound(DoomSounds.CACODEMON_AFFECTIONATE_SCREAM, 1.0F, 1.2F / (getRandom().nextFloat() * 0.2F + 0.9F)), 1));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.0F;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
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

	@Override
	public boolean onClimbable() {
		return false;
	}

	static class LookAroundGoal extends Goal {
		private final CacodemonEntity parentEntity;

		public LookAroundGoal(CacodemonEntity ghast) {
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

	static class GhastMoveControl extends MoveControl {
		private final CacodemonEntity parentEntity;
		private int courseChangeCooldown;

		public GhastMoveControl(CacodemonEntity cacodemonEntity) {
			super(cacodemonEntity);
			parentEntity = cacodemonEntity;
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

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.CACODEMON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.CACODEMON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.CACODEMON_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 2;
	}

}