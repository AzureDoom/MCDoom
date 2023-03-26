package mod.azure.doom.entity.tierheavy;

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
import mod.azure.doom.entity.ai.goal.MancubusFireAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.FireProjectile;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MancubusEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(MancubusEntity.class, EntityDataSerializers.INT);
	private int attackTimer;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public MancubusEntity(EntityType<MancubusEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP, SoundSource.HOSTILE, 0.25F, 1.0F, false);
			if (event.getKeyframeData().getSound().matches("talk"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.MANCUBUS_STEP, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("firing", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("ground", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.ROCKET_FIRING, SoundSource.HOSTILE, 0.25F, 1.0F, true);
			if (event.getKeyframeData().getSound().matches("flames"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE, 0.25F, 1.0F, true);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 80) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, IronGolem.class, 8.0F));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		applyEntityAI();
	}

	protected void applyEntityAI() {
		goalSelector.addGoal(4, new MancubusFireAttackGoal(this, new FireballAttack(this).setProjectileOriginOffset(0.1, 0.5, 0.1).setDamage(DoomConfig.mancubus_ranged_damage)));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
	}

	public class FireballAttack extends AbstractRangedAttack {

		public FireballAttack(MancubusEntity parentEntity, double xOffSetModifier, double entityHeightFraction, double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(MancubusEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(SoundEvents.CAMPFIRE_CRACKLE, 1, 1);
		}

		@Override
		public Projectile getProjectile(Level world, double d2, double d3, double d4) {
			return new FireProjectile(world, parentEntity, d2, d3, d4, damage);

		}
	}

	@Override
	protected void updateControlFlags() {
		final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
		goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockpos = BlockPos.containing(x, y, z);
		boolean flag = false;
		double d0 = 0.0D;
		do {
			final BlockPos blockpos1 = blockpos.below();
			final BlockState blockstate = level.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
				if (!level.isEmptyBlock(blockpos)) {
					final BlockState blockstate1 = level.getBlockState(blockpos);
					final VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}
				flag = true;
				break;
			}
			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(maxY) - 1);

		if (flag) {
			final DoomFireEntity fang = new DoomFireEntity(level, x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.mancubus_ranged_damage);
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.MAX_HEALTH, DoomConfig.mancubus_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 2.80F;
	}

	public int getAttackTimer() {
		return attackTimer;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MANCUBUS_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MANCUBUS_DEATH;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
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
		return Mth.clamp(entityData.get(VARIANT), 1, 5);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 5;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final SplittableRandom random = new SplittableRandom();
		final int var = random.nextInt(0, 6);
		setVariant(var);
		return spawnDataIn;
	}

	@Override
	public int getArmorValue() {
		return getVariant() >= 4 ? 6 : 0;
	}

}