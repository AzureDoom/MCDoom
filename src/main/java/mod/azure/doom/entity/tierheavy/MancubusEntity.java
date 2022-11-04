package mod.azure.doom.entity.tierheavy;

import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.MancubusFireAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.FireProjectile;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
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

public class MancubusEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(MancubusEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public MancubusEntity(EntityType<MancubusEntity> entityType, World worldIn) {
		super(entityType, worldIn);
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
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("firing", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("ground", EDefaultLoopTypes.PLAY_ONCE));
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
			this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.ROCKET_FIRING,
					SoundCategory.HOSTILE, 0.25F, 1.0F, true);
		}
		if (event.sound.matches("flames") && this.dataTracker.get(STATE) > 1) {
			this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_FIRECHARGE_USE,
					SoundCategory.HOSTILE, 0.25F, 1.0F, true);
		}
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (this.world.isClient) {
			if (event.sound.matches("walk")) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
			if (event.sound.matches("talk")) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.MANCUBUS_STEP,
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
		this.goalSelector.add(4, new MancubusFireAttackGoal(this, new FireballAttack(this)
				.setProjectileOriginOffset(0.1, 0.5, 0.1).setDamage(DoomConfig.mancubus_ranged_damage)));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public class FireballAttack extends AbstractRangedAttack {

		public FireballAttack(MancubusEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
				double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(MancubusEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(SoundEvents.BLOCK_CAMPFIRE_CRACKLE, 1, 1);
		}

		@Override
		public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
			return new FireProjectile(world, this.parentEntity, d2, d3, d4, damage);

		}
	}

	@Override
	protected void updateGoalControls() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlEnabled(Goal.Control.LOOK, flag);
		super.updateGoalControls();
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
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
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

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 2);
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 3);
		this.setVariant(var);
		return entityData;
	}

	@Override
	public int getArmor() {
		return this.getVariant() == 2 ? 6 : 0;
	}
}