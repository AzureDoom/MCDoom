package mod.azure.doom.entity.tiersuperheavy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.TypeFilter;
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

public class SummonerEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(SummonerEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private int ageWhenTargetSet;

	public SummonerEntity(EntityType<SummonerEntity> entityType, World worldIn) {
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
			event.getController().setAnimation(new AnimationBuilder().addAnimation("summon", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<SummonerEntity> controller = new AnimationController<SummonerEntity>(this, "controller", 0,
				this::predicate);
		AnimationController<SummonerEntity> controller1 = new AnimationController<SummonerEntity>(this, "controller1",
				0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.world.isClient) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PHANTOM_SWOOP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
		if (event.sound.matches("attack")) {
			if (this.world.isClient) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.ARCHVILE_SCREAM,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4, new SummonerEntity.AttackGoal(this));
		this.targetSelector.add(1, new SummonerEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
		this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, true));
	}

	static class AttackGoal extends Goal {
		private final SummonerEntity entity;
		public int cooldown;
		private int seeTime;
		private boolean strafingClockwise;
		private boolean strafingBackwards;
		private int strafingTime = -1;
		private float maxAttackDistance = 20;
		private int strafeTicks = 20;

		public AttackGoal(SummonerEntity ghast) {
			this.entity = ghast;
		}

		public boolean canStart() {
			return this.entity.getTarget() != null;
		}

		public void start() {
			super.start();
			this.entity.setAttacking(true);
			this.cooldown = 0;
			this.entity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.entity.setAttacking(false);
			this.entity.setAttackingState(0);
			this.seeTime = 0;
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			++this.cooldown;
			double distanceToTargetSq = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(),
					livingentity.getZ());
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			if (inLineOfSight != this.seeTime > 0) {
				this.seeTime = 0;
			}

			if (inLineOfSight) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (distanceToTargetSq <= (double) this.maxAttackDistance && this.seeTime >= 20) {
				this.entity.getNavigation().stop();
				++this.strafingTime;
			} else {
				this.entity.getNavigation().startMovingTo(livingentity, 0.95F);
				this.entity.getMoveControl().strafeTo(this.strafingBackwards ? -0.5F : 0.5F,
						this.strafingClockwise ? 0.5F : -0.5F);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= strafeTicks) {
				if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (distanceToTargetSq > (double) (this.maxAttackDistance * 0.75F)) {
					this.strafingBackwards = false;
				} else if (distanceToTargetSq < (double) (this.maxAttackDistance * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.entity.getMoveControl().strafeTo(this.strafingBackwards ? -0.5F : 0.5F,
						this.strafingClockwise ? 0.5F : -0.5F);
				this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			} else {
				this.entity.getLookControl().lookAt(livingentity, 30.0F, 30.0F);
			}
			if (this.cooldown == 40) {
				if (!this.entity.world.isClient) {
					final Box aabb = new Box(this.entity.getBlockPos()).expand(64D);
					int i = this.entity.world
							.getEntitiesByType(TypeFilter.instanceOf(DemonEntity.class), aabb, Entity::isAlive).size();
					double d = Math.min(livingentity.getY(), this.entity.getY());
					double e = Math.max(livingentity.getY(), this.entity.getY()) + 1.0D;
					float f = (float) MathHelper.atan2(livingentity.getZ() - this.entity.getZ(),
							livingentity.getX() - this.entity.getX());
					int j;
					SplittableRandom random = new SplittableRandom();
					int r = random.nextInt(0, 40);
					if (r >= 17) {
						for (j = 0; j < 16; ++j) {
							double l1 = 1.25D * (double) (j + 1);
							this.entity.conjureFangs(this.entity.getX() + (double) MathHelper.cos(f) * l1,
									this.entity.getZ() + (double) MathHelper.sin(f) * l1, d, e, f, 32);
						}
					} else {
						if (i <= 15)
							this.entity.spawnWave();
					}
				}
				this.entity.setAttackingState(1);
			}
			if (this.cooldown >= 60) {
				this.entity.setAttackingState(0);
				this.cooldown = -5;
			}
			this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
		}
	}

	public void spawnWave() {
		List<EntityType<?>> givenList = Arrays.asList(DoomEntities.IMP, DoomEntities.LOST_SOUL, DoomEntities.IMP_STONE);
		int r = this.random.range(-3, 3);

		for (int k = 1; k < 5; ++k) {
			for (int i = 0; i < 1; i++) {
				int randomIndex = this.random.nextInt(givenList.size());
				EntityType<?> randomElement = givenList.get(randomIndex);
				Entity fireballentity = randomElement.create(world);
				fireballentity.refreshPositionAndAngles(this.getX() + r, this.getY() + 0.5D, this.getZ() + r, 0, 0);
				world.spawnEntity(fireballentity);
			}
		}
	}

	static class TeleportTowardsPlayerGoal extends TargetGoal<PlayerEntity> {
		private final SummonerEntity enderman;
		private PlayerEntity targetPlayer;
		private int lookAtPlayerWarmup;
		private int ticksSinceUnseenTeleport;
		private final TargetPredicate staringPlayerPredicate;
		private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();

		public TeleportTowardsPlayerGoal(SummonerEntity enderman, @Nullable Predicate<LivingEntity> predicate) {
			super(enderman, PlayerEntity.class, 10, false, false, predicate);
			this.enderman = enderman;
			this.staringPlayerPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange())
					.setPredicate((playerEntity) -> {
						return enderman.isPlayerStaring((PlayerEntity) playerEntity);
					});
		}

		public boolean canStart() {
			this.targetPlayer = this.enderman.world.getClosestPlayer(this.staringPlayerPredicate, this.enderman);
			return this.targetPlayer != null;
		}

		public void start() {
			this.lookAtPlayerWarmup = 5;
			this.ticksSinceUnseenTeleport = 0;
		}

		public void stop() {
			this.targetPlayer = null;
			super.stop();
		}

		public boolean shouldContinue() {
			if (this.targetPlayer != null) {
				if (!this.enderman.isPlayerStaring(this.targetPlayer)) {
					return false;
				} else {
					this.enderman.lookAtEntity(this.targetPlayer, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.targetEntity != null && this.validTargetPredicate.test(this.enderman, this.targetEntity)
						? true
						: super.shouldContinue();
			}
		}

		public void tick() {
			if (this.enderman.getTarget() == null) {
				super.setTargetEntity((LivingEntity) null);
			}

			if (this.targetPlayer != null) {
				if (--this.lookAtPlayerWarmup <= 0) {
					this.targetEntity = this.targetPlayer;
					this.targetPlayer = null;
					super.start();
				}
			} else {
				if (this.targetEntity != null && !this.enderman.hasVehicle()) {
					if (this.enderman.isPlayerStaring((PlayerEntity) this.targetEntity)) {

						this.ticksSinceUnseenTeleport = 0;
					} else if (this.targetEntity.squaredDistanceTo(this.enderman) > 256.0D
							&& this.ticksSinceUnseenTeleport++ >= 30 && this.enderman.teleportTo(this.targetEntity)) {
						this.ticksSinceUnseenTeleport = 0;
					}
				}

				super.tick();
			}

		}
	}

	private boolean isPlayerStaring(PlayerEntity player) {
		ItemStack itemStack = (ItemStack) player.getInventory().armor.get(3);
		if (itemStack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
			return false;
		} else {
			Vec3d vec3d = player.getRotationVec(1.0F).normalize();
			Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(),
					this.getZ() - player.getZ());
			double d = vec3d2.length();
			vec3d2 = vec3d2.normalize();
			double e = vec3d.dotProduct(vec3d2);
			return e > 1.0D - 0.025D / d ? player.canSee(this) : false;
		}
	}

	@Override
	protected void mobTick() {
		if (this.world.isDay() && this.age >= this.ageWhenTargetSet + 600) {
			float f = this.getLightLevelDependentValue();
			if (f > 0.5F && this.world.isSkyVisible(this.getBlockPos())
					&& this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
			}
		}

		super.mobTick();
	}

	protected boolean teleportRandomly() {
		if (!this.world.isClient() && this.isAlive()) {
			double d = this.getX() + (this.random.nextDouble() - 0.5D) * 10.0D;
			double e = this.getY() + (double) (this.random.nextInt(64) - 10);
			double f = this.getZ() + (this.random.nextDouble() - 0.5D) * 10.0D;
			return this.teleportTo(d, e, f);
		} else {
			return false;
		}
	}

	private boolean teleportTo(Entity entity) {
		Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5D) - entity.getEyeY(),
				this.getZ() - entity.getZ());
		vec3d = vec3d.normalize();
		double e = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.x * 10.0D;
		double f = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3d.y * 10.0D;
		double g = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.z * 10.0D;
		return this.teleportTo(e, f, g);
	}

	private boolean teleportTo(double x, double y, double z) {
		BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

		while (mutable.getY() > 0 && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
			mutable.move(Direction.DOWN);
		}

		BlockState blockState = this.world.getBlockState(mutable);
		boolean bl = blockState.getMaterial().blocksMovement();
		boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
		if (bl && !bl2) {
			boolean bl3 = this.teleport(x, y, z, true);

			return bl3;
		} else {
			return false;
		}
	}

	private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockPos = new BlockPos(x, y, z);
		boolean bl = false;
		double d = 0.0D;

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
					DoomConfig.summoner_ranged_damage);
			fang.setFireTicks(age);
			fang.isInvisible();
			this.world.spawnEntity(fang);
		}

	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
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
		this.setVariant(this.random.nextInt());
		return entityData;
	}

	public static boolean spawning(EntityType<SummonerEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.summoner_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected void updateGoalControls() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlEnabled(Goal.Control.LOOK, flag);
		super.updateGoalControls();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.ARCHVILE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ARCHVILE_DEATH;
	}
}