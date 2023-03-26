package mod.azure.doom.entity.tierfodder;

import java.util.EnumSet;
import java.util.SplittableRandom;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
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
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LostSoulEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(LostSoulEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(LostSoulEntity.class, EntityDataSerializers.INT);
	public int explosionPower = 1;
	public int flameTimer;

	public LostSoulEntity(EntityType<? extends LostSoulEntity> type, Level world) {
		super(type, world);
		moveControl = new LostSoulEntity.MoveHelperController(this);
		setMaxUpStep(4.0F);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_FLAGS_ID, (byte) 0);
		entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setVariant(tag.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());
	}

	public int getVariant() {
		return Mth.clamp(entityData.get(VARIANT), 1, 3);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 3;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final SplittableRandom random = new SplittableRandom();
		final int var = random.nextInt(0, 4);
		setVariant(var);
		return spawnDataIn;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.lost_soul_health).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, DoomConfig.lost_soul_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(8, new LostSoulEntity.LookAroundGoal(this));
		goalSelector.addGoal(4, new DemonAttackGoal(this, 6.25D, 2));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 5) {
			remove(RemovalReason.KILLED);
			dropExperience();
			if (!level.isClientSide) {
				explode();
			}
		}
	}

	protected void explode() {
		level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, Level.ExplosionInteraction.NONE);
	}

	private boolean getVexFlag(int p_190656_1_) {
		final int i = entityData.get(DATA_FLAGS_ID);
		return (i & p_190656_1_) != 0;
	}

	private void setVexFlag(int p_190660_1_, boolean p_190660_2_) {
		int i = entityData.get(DATA_FLAGS_ID);
		if (p_190660_2_) {
			i = i | p_190660_1_;
		} else {
			i = i & ~p_190660_1_;
		}

		entityData.set(DATA_FLAGS_ID, (byte) (i & 255));
	}

	public boolean isCharging() {
		return getVexFlag(1);
	}

	public void setCharging(boolean charging) {
		setVexFlag(1, charging);
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public boolean onClimbable() {
		return true;
	}

	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		final FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
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
		if (tickCount % 10 == 0) {
			refreshDimensions();
		}
	}

	@Override
	protected void updateControlFlags() {
		final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
		goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 8;

		final boolean isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(this, isInsideWaterBlock);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	static class MoveHelperController extends MoveControl {
		private final LostSoulEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(LostSoulEntity lostSoulEntity) {
			super(lostSoulEntity);
			parentEntity = lostSoulEntity;
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
						parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d.scale(0.2D)));
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

	static class LookAroundGoal extends Goal {
		private final LostSoulEntity parentEntity;

		public LookAroundGoal(LostSoulEntity ghast) {
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

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.LOST_SOUL_DEATH;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.LOST_SOUL_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return getVariant() == 3 ? EntityDimensions.scalable(1.0F, 1.5F) : super.getDimensions(pose);
	}

}