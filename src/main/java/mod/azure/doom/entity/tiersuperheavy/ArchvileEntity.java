package mod.azure.doom.entity.tiersuperheavy;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
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
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ArchvileEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(ArchvileEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private int ageWhenTargetSet;
	public int flameTimer;

	public ArchvileEntity(EntityType<ArchvileEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public static boolean spawning(EntityType<ArchvileEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (!world.isClient) {
			float q = 200.0F;
			int k = MathHelper.floor(this.getX() - (double) q - 1.0D);
			int l = MathHelper.floor(this.getX() + (double) q + 1.0D);
			int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
			int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
			int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
			int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
			List<Entity> list = this.world.getOtherEntities(this,
					new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
			for (int k2 = 0; k2 < list.size(); ++k2) {
				Entity entity = list.get(k2);
				if (entity.isAlive()) {
					entity.setGlowing(false);
				}
			}
		}
		if (this.deathTime == 50) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.velocityModified) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<ArchvileEntity> controller = new AnimationController<ArchvileEntity>(this, "controller", 0,
				this::predicate);
		AnimationController<ArchvileEntity> controller1 = new AnimationController<ArchvileEntity>(this, "controller1",
				0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.world.isClient) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
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

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 8;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.archvile_health)
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
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(7, new ArchvileEntity.AttackGoal(this));
		this.targetSelector.add(1, new ArchvileEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
		this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, true));
	}

	@Override
	protected void updateGoalControls() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlEnabled(Goal.Control.LOOK, flag);
		super.updateGoalControls();
	}

	static class AttackGoal extends Goal {
		private final ArchvileEntity ghast;
		public int cooldown;

		public AttackGoal(ArchvileEntity ghast) {
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
				if (this.cooldown == 20) {
					if (!this.ghast.world.isClient) {
						float q = 24.0F;
						int k = MathHelper.floor(this.ghast.getX() - (double) q - 1.0D);
						int l = MathHelper.floor(this.ghast.getX() + (double) q + 1.0D);
						int t = MathHelper.floor(this.ghast.getY() - (double) q - 1.0D);
						int u = MathHelper.floor(this.ghast.getY() + (double) q + 1.0D);
						int v = MathHelper.floor(this.ghast.getZ() - (double) q - 1.0D);
						int w = MathHelper.floor(this.ghast.getZ() + (double) q + 1.0D);
						List<Entity> list = this.ghast.world.getOtherEntities(this.ghast,
								new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
						Vec3d vec3d1 = new Vec3d(this.ghast.getX(), this.ghast.getY(), this.ghast.getZ());

						for (int x = 0; x < list.size(); ++x) {
							Entity entity = (Entity) list.get(x);
							if ((entity instanceof MobEntity)) {
								float y = (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d1)) / q);
								if (y <= 1.0D) {
									((MobEntity) entity)
											.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000, 1));
									entity.setGlowing(true);
								}
							}
						}
						double d = Math.min(livingEntity.getY(), ghast.getY());
						double e = Math.max(livingEntity.getY(), ghast.getY()) + 1.0D;
						float f = (float) MathHelper.atan2(livingEntity.getZ() - ghast.getZ(),
								livingEntity.getX() - ghast.getX());
						int j;
						if (ghast.squaredDistanceTo(livingEntity) < 9.0D) {
							float h;
							for (j = 0; j < 15; ++j) {
								h = f + (float) j * 3.1415927F * 0.4F;
								ghast.conjureFangs(ghast.getX() + (double) MathHelper.cos(h) * 1.5D,
										ghast.getZ() + (double) MathHelper.sin(h) * 1.5D, d, e, h, 0);
							}

							for (j = 0; j < 18; ++j) {
								h = f + (float) j * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
								ghast.conjureFangs(ghast.getX() + (double) MathHelper.cos(h) * 2.5D,
										ghast.getZ() + (double) MathHelper.sin(h) * 2.5D, d, e, h, 3);
							}
						} else {
							for (j = 0; j < 26; ++j) {
								double l1 = 1.25D * (double) (j + 1);
								ghast.conjureFangs(ghast.getX() + (double) MathHelper.cos(f) * l1,
										ghast.getZ() + (double) MathHelper.sin(f) * l1, d, e, f, 32);
							}
						}
					}
					if (!(this.ghast.world.isClient)) {
						this.ghast.playSound(DoomSounds.ARCHVILE_SCREAM, 1.0F,
								1.2F / (this.ghast.random.nextFloat() * 0.2F + 0.9F));
					}
					this.ghast.setAttackingState(1);
				}
				if (this.cooldown == 40) {
					this.ghast.setAttackingState(0);
					this.cooldown = -5;
				}
			} else if (this.cooldown > 0) {
				--this.cooldown;
			}
			this.ghast.lookAtEntity(livingEntity, 30.0F, 30.0F);
		}
	}

	static class TeleportTowardsPlayerGoal extends TargetGoal<PlayerEntity> {
		private final ArchvileEntity enderman;
		private PlayerEntity targetPlayer;
		private int lookAtPlayerWarmup;
		private int ticksSinceUnseenTeleport;
		private final TargetPredicate staringPlayerPredicate;
		private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();

		public TeleportTowardsPlayerGoal(ArchvileEntity enderman, @Nullable Predicate<LivingEntity> predicate) {
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
			DoomFireEntity fang = new DoomFireEntity(this.world, x, (double) blockPos.getY() + d, z, yaw, warmup, this,
					DoomConfig.archvile_ranged_damage);
			fang.setFireTicks(age);
			fang.isInvisible();
			this.world.spawnEntity(fang);
		}

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