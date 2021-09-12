package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.Random;

import com.mojang.math.Vector3d;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import mod.azure.doom.util.config.Config;
import mod.azure.doom.util.config.EntityConfig;
import mod.azure.doom.util.config.EntityDefaults.EntityConfigType;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.shadowed.eliotlash.mclib.utils.MathHelper;

public class PainEntity extends DemonEntity implements Enemy, IAnimatable {
	public static EntityConfig config = Config.SERVER.entityConfig.get(EntityConfigType.PAIN);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PainEntity.class,
			EntityDataSerializers.INT);

	public PainEntity(EntityType<? extends PainEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new PainEntity.MoveHelperController(this);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<PainEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<PainEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setVariant(compound.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 2);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setVariant(this.random.nextInt());
		return spawnDataIn;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return config.pushAttributes(Mob.createMobAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 50D));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.goalSelector.addGoal(7, new PainEntity.LookAroundGoal(this));
		this.goalSelector.addGoal(7, new PainEntity.FireballAttackGoal(this, 1));
		this.goalSelector.addGoal(7, new DemonAttackGoal(this, 1.0D, false, 2));
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213812_1_) -> {
					return Math.abs(p_213812_1_.getY() - this.getY()) <= 4.0D;
				}));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, 10, true,
				false, (p_213812_1_) -> {
					return Math.abs(p_213812_1_.getY() - this.getY()) <= 4.0D;
				}));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove();
			if (!this.level.isClientSide()) {
				LostSoulEntity lost_soul = ModEntityTypes.LOST_SOUL.get().create(level);
				lost_soul.moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
				this.level.addFreshEntity(lost_soul);
				LostSoulEntity lost_soul1 = ModEntityTypes.LOST_SOUL.get().create(level);
				lost_soul1.moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
				this.level.addFreshEntity(lost_soul1);
				LostSoulEntity lost_soul2 = ModEntityTypes.LOST_SOUL.get().create(level);
				lost_soul2.moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
				this.level.addFreshEntity(lost_soul2);
			}
		}
	}

	public static boolean spawning(EntityType<PainEntity> p_223368_0_, LevelAccessor p_223337_1_,
			MobSpawnType reason, BlockPos p_223368_3_, Random p_223368_4_) {
		return passPeacefulAndYCheck(config, p_223337_1_, reason, p_223368_3_, p_223368_4_)
				&& p_223368_4_.nextInt(20) == 0
				&& checkMobSpawnRules(p_223368_0_, p_223337_1_, reason, p_223368_3_, p_223368_4_);
	}

	public int getFireballStrength() {
		return 1;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public void travel(Vector3d travelVector) {
		if (this.isInWater()) {
			this.moveRelative(0.02F, travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
		} else if (this.isInLava()) {
			this.moveRelative(0.02F, travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
		} else {
			BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
			float f = 0.91F;
			if (this.onGround) {
				f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
			}

			float f1 = 0.16277137F / (f * f * f);
			f = 0.91F;
			if (this.onGround) {
				f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
			}

			this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
		}

		this.calculateEntityAnimation(this, false);
	}

	public boolean onClimbable() {
		return false;
	}

	static class FireballAttackGoal extends Goal {
		private final PainEntity parentEntity;
		public int attackTimer;
		private int statecheck;

		public FireballAttackGoal(PainEntity ghast, int state) {
			this.parentEntity = ghast;
			this.statecheck = state;
		}

		public boolean canUse() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			this.attackTimer = 0;
		}

		public void stop() {
			this.parentEntity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingentity = this.parentEntity.getTarget();
			if (livingentity.distanceToSqr(this.parentEntity) < 4096.0D && this.parentEntity.canSee(livingentity)) {
				this.parentEntity.getLookControl().setLookAt(livingentity, 90.0F, 30.0F);
				World world = this.parentEntity.level;
				++this.attackTimer;
				if (this.attackTimer == 20) {
					if (this.parentEntity.getVariant() == 1) {
						LostSoulEntity lost_soul = ModEntityTypes.LOST_SOUL.get().create(world);
						lost_soul.moveTo(this.parentEntity.getX(), this.parentEntity.getY(), this.parentEntity.getZ(),
								0, 0);
						lost_soul.push(1.0D, 0.0D, 0.0D);
						world.addFreshEntity(lost_soul);
					} else {
						LostSoulEntity lost_soul = ModEntityTypes.LOST_SOUL.get().create(world);
						lost_soul.moveTo(this.parentEntity.getX(), this.parentEntity.getY(), this.parentEntity.getZ(),
								0, 0);
						lost_soul.push(1.0D, 0.0D, 0.0D);
						world.addFreshEntity(lost_soul);
						LostSoulEntity lost_soul1 = ModEntityTypes.LOST_SOUL.get().create(world);
						lost_soul1.moveTo(this.parentEntity.getX(), this.parentEntity.getY(), this.parentEntity.getZ(),
								0, 0);
						lost_soul1.push(1.0D, 0.0D, 0.0D);
						world.addFreshEntity(lost_soul1);
					}
					this.parentEntity.setAttackingState(1);
				}
				if (this.attackTimer == 35) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -45;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.setAttackingState(attackTimer >= 10 ? this.statecheck : 0);
		}
	}

	static class LookAroundGoal extends Goal {
		private final PainEntity parentEntity;

		public LookAroundGoal(PainEntity ghast) {
			this.parentEntity = ghast;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.parentEntity.getTarget() == null) {
				Vector3d vec3d = this.parentEntity.getDeltaMovement();
				this.parentEntity.yRot = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * (180F / (float) Math.PI);
				this.parentEntity.yBodyRot = this.parentEntity.yRot;
			} else {
				LivingEntity livingentity = this.parentEntity.getTarget();
				if (livingentity.distanceToSqr(this.parentEntity) < 4096.0D) {
					double d1 = livingentity.getX() - this.parentEntity.getX();
					double d2 = livingentity.getZ() - this.parentEntity.getZ();
					this.parentEntity.yRot = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.parentEntity.yBodyRot = this.parentEntity.yRot;
				}
			}

		}
	}

	static class MoveHelperController extends MovementController {
		private final PainEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(PainEntity ghast) {
			super(ghast);
			this.parentEntity = ghast;
		}

		public void tick() {
			if (this.operation == MovementController.Action.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
					Vector3d vector3d = new Vector3d(this.wantedX - this.parentEntity.getX(),
							this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
					double d0 = vector3d.length();
					vector3d = vector3d.normalize();
					if (this.canReach(vector3d, MathHelper.ceil(d0))) {
						this.parentEntity
								.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
					} else {
						this.operation = MovementController.Action.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vector3d p_220673_1_, int p_220673_2_) {
			AABB axisalignedbb = this.parentEntity.getBoundingBox();

			for (int i = 1; i < p_220673_2_; ++i) {
				axisalignedbb = axisalignedbb.move(p_220673_1_);
				if (!this.parentEntity.level.noCollision(this.parentEntity, axisalignedbb)) {
					return false;
				}
			}

			return true;
		}
	}

	static class RandomFlyGoal extends Goal {
		private final PainEntity parentEntity;

		public RandomFlyGoal(PainEntity ghast) {
			this.parentEntity = ghast;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			MovementController movementcontroller = this.parentEntity.getMoveControl();
			if (!movementcontroller.hasWanted()) {
				return true;
			} else {
				double d0 = movementcontroller.getWantedX() - this.parentEntity.getX();
				double d1 = movementcontroller.getWantedY() - this.parentEntity.getY();
				double d2 = movementcontroller.getWantedZ() - this.parentEntity.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 10.0D;
			}
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void start() {
			Random random = this.parentEntity.getRandom();
			double d0 = this.parentEntity.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
			double d1 = this.parentEntity.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
			double d2 = this.parentEntity.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
			this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
		}
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 1.0F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.PAIN_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.PAIN_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.PAIN_DEATH.get();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
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