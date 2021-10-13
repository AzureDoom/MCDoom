package mod.azure.doom.entity.tiersuperheavy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SummonerEntity extends DemonEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(SummonerEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private int ageWhenTargetSet;

	public SummonerEntity(EntityType<SummonerEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("summon", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<SummonerEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<SummonerEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4, new SummonerEntity.AttackGoal(this));
		this.goalSelector.add(4, new DemonAttackGoal(this, 1.0D, false, 2));
		this.targetSelector.add(1, new SummonerEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, MerchantEntity.class, true));
	}

	static class AttackGoal extends Goal {
		private final SummonerEntity ghast;
		public int cooldown;

		public AttackGoal(SummonerEntity ghast) {
			this.ghast = ghast;
		}

		public boolean canStart() {
			return this.ghast.getTarget() != null;
		}

		public void start() {
			super.start();
			this.ghast.setAttacking(true);
			this.cooldown = 0;
			this.ghast.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.ghast.setAttacking(false);
			this.ghast.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingEntity = this.ghast.getTarget();
			if (this.ghast.canSee(livingEntity)) {
				++this.cooldown;
				if (this.cooldown == 40) {
					if (!this.ghast.world.isClient) {
						double d = Math.min(livingEntity.getY(), ghast.getY());
						double e = Math.max(livingEntity.getY(), ghast.getY()) + 1.0D;
						float f = (float) MathHelper.atan2(livingEntity.getZ() - ghast.getZ(),
								livingEntity.getX() - ghast.getX());
						int j;
						if (ghast.squaredDistanceTo(livingEntity) > 13.0D) {
							for (j = 0; j < 16; ++j) {
								double l1 = 1.25D * (double) (j + 1);
								ghast.conjureFangs(ghast.getX() + (double) MathHelper.cos(f) * l1,
										ghast.getZ() + (double) MathHelper.sin(f) * l1, d, e, f, 32);
							}
						} else {
							ghast.spawnWave();
						}
					}
					this.ghast.setAttackingState(1);
				}
				if (this.cooldown == 60) {
					this.ghast.setAttackingState(0);
					this.cooldown = -800;
				}
			} else if (this.cooldown > 0) {
				--this.cooldown;
			}
			this.ghast.lookAtEntity(livingEntity, 30.0F, 30.0F);
		}
	}

	public void spawnWave() {
		Random rand = new Random();
		List<EntityType<?>> givenList = Arrays.asList(ModEntityTypes.IMP, ModEntityTypes.NIGHTMARE_IMP,
				ModEntityTypes.IMP2016, ModEntityTypes.LOST_SOUL, ModEntityTypes.IMP_STONE);

		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity = randomElement.create(world);
			fireballentity.refreshPositionAndAngles(this.getX() + 2.0D, this.getY() + 0.5D, this.getZ() + 2.0D, 0, 0);
			world.spawnEntity(fireballentity);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity1 = randomElement.create(world);
			fireballentity1.refreshPositionAndAngles(this.getX() + -2.0D, this.getY() + 0.5D, this.getZ() + -2.0D, 0,
					0);
			world.spawnEntity(fireballentity1);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity11 = randomElement.create(world);
			fireballentity11.refreshPositionAndAngles(this.getX() + 1.0D, this.getY() + 0.5D, this.getZ() + 1.0D, 0, 0);
			world.spawnEntity(fireballentity11);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity111 = randomElement.create(world);
			fireballentity111.refreshPositionAndAngles(this.getX() + -1.0D, this.getY() + 0.5D, this.getZ() + -1.0D, 0,
					0);
			world.spawnEntity(fireballentity111);
		}
	}

	static class TeleportTowardsPlayerGoal extends FollowTargetGoal<PlayerEntity> {
		private final SummonerEntity enderman;
		private PlayerEntity targetPlayer;
		private int lookAtPlayerWarmup;
		private int ticksSinceUnseenTeleport;
		private final TargetPredicate staringPlayerPredicate;
		private final TargetPredicate validTargetPredicate = (new TargetPredicate()).includeHidden();

		public TeleportTowardsPlayerGoal(SummonerEntity enderman, @Nullable Predicate<LivingEntity> predicate) {
			super(enderman, PlayerEntity.class, 10, false, false, predicate);
			this.enderman = enderman;
			this.staringPlayerPredicate = (new TargetPredicate()).setBaseMaxDistance(this.getFollowRange())
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
						if (this.targetEntity.squaredDistanceTo(this.enderman) < 16.0D) {
							this.enderman.teleportRandomly();
						}

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
		ItemStack itemStack = (ItemStack) player.inventory.armor.get(3);
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
			float f = this.getBrightnessAtEyes();
			if (f > 0.5F && this.world.isSkyVisible(this.getBlockPos())
					&& this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleportRandomly();
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
			DoomFireEntity fang = new DoomFireEntity(this.world, x, (double) blockPos.getY() + d, z, yaw, warmup, this, config.summoner_ranged_damage);
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
			this.remove();
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.summoner_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
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
}