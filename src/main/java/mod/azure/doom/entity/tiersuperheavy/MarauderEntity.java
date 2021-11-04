package mod.azure.doom.entity.tiersuperheavy;

import java.util.Random;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.ai.goal.RangedShotgunAttackGoal;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.item.ammo.ShellAmmo;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MarauderEntity extends DemonEntity implements IAnimatable, RangedAttackMob, IAnimationTickable {

	private final RangedShotgunAttackGoal<MarauderEntity> bowAttackGoal = new RangedShotgunAttackGoal<>(this, 1.0D, 20,
			15.0F, 2);
	private final DemonAttackGoal meleeAttackGoal = new DemonAttackGoal(this, 1.2D, false, 1) {
		public void stop() {
			super.stop();
			MarauderEntity.this.setAttacking(false);
		}

		public void start() {
			super.start();
			MarauderEntity.this.setAttacking(true);
		}
	};
	private AnimationFactory factory = new AnimationFactory(this);
	private int ageWhenTargetSet;

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("ranged", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
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

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<MarauderEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<MarauderEntity>(this, "controller1", 0, this::predicate1));
		data.addAnimationController(new AnimationController<MarauderEntity>(this, "controller2", 0, this::predicate2));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public MarauderEntity(EntityType<MarauderEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.updateAttackType();
	}

	public static boolean spawning(EntityType<MarauderEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.targetSelector.add(1, new MarauderEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	static class TeleportTowardsPlayerGoal extends FollowTargetGoal<PlayerEntity> {
		private final MarauderEntity enderman;
		private PlayerEntity targetPlayer;
		private int lookAtPlayerWarmup;
		private int ticksSinceUnseenTeleport;
		private final TargetPredicate staringPlayerPredicate;
		private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();

		public TeleportTowardsPlayerGoal(MarauderEntity enderman, @Nullable Predicate<LivingEntity> predicate) {
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

	public boolean isPlayerStaring(PlayerEntity player) {
		Vec3d vec3d = player.getRotationVec(1.0F).normalize();
		Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(),
				this.getZ() - player.getZ());
		double d = vec3d2.length();
		vec3d2 = vec3d2.normalize();
		double e = vec3d.dotProduct(vec3d2);
		return e > 1.0D - 0.025D / d ? player.canSee(this) : false;
	}

	@Override
	public void tickMovement() {
		if (this.world.isClient) {
			for (int i = 0; i < 2; ++i) {
				this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D,
						this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
						(this.random.nextDouble() - 0.5D) * 2.0D);
			}
		}
		this.jumping = false;
		if (!this.world.isClient) {
			this.tickAngerLogic((ServerWorld) this.world, true);
		}
		super.tickMovement();
	}

	@Override
	protected void mobTick() {
		if (this.world.isDay() && this.age >= this.ageWhenTargetSet + 600) {
			float f = this.getBrightnessAtEyes();
			if (f > 0.5F && this.world.isSkyVisible(this.getBlockPos())
					&& this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
			}
		}
		super.mobTick();
	}

	protected boolean teleportRandomly() {
		if (!this.world.isClient() && this.isAlive()) {
			double d = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
			double e = this.getY() + (double) (this.random.nextInt(64) - 32);
			double f = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
			return this.teleportTo(d, e, f);
		} else {
			return false;
		}
	}

	private boolean teleportTo(Entity entity) {
		Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5D) - entity.getEyeY(),
				this.getZ() - entity.getZ());
		vec3d = vec3d.normalize();
		double e = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
		double f = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3d.y * 16.0D;
		double g = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
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

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.marauder_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.marauder_axe_damage).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected void initEquipment(LocalDifficulty difficulty) {
		super.initEquipment(difficulty);
		this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(DoomItems.SG));
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		this.updateAttackType();
		this.initEquipment(difficulty);
		return entityData;
	}

	public void updateAttackType() {
		if (this.world != null && !this.world.isClient) {
			this.goalSelector.remove(this.meleeAttackGoal);
			this.goalSelector.remove(this.bowAttackGoal);
			ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, DoomItems.SG));
			if (itemStack.getItem() == DoomItems.SG) {
				int i = 40;
				if (this.world.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}
				this.bowAttackGoal.setAttackInterval(i);
				this.goalSelector.add(4, this.bowAttackGoal);
			} else {
				this.goalSelector.add(4, this.meleeAttackGoal);
			}
		}
	}

	public void attack(LivingEntity target, float pullProgress) {
		ItemStack itemStack = this
				.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, DoomItems.SG)));
		ShotgunShellEntity ShotgunShellEntity = this.createArrowProjectile(itemStack, pullProgress);
		double d = target.getX() - this.getX();
		double e = target.getBodyY(0.3333333333333333D) - ShotgunShellEntity.getY();
		double f = target.getZ() - this.getZ();
		float g = MathHelper.sqrt((float) (d * d + f * f));
		ShotgunShellEntity.setVelocity(d, e + g * 0.05F, f, 1.6F, 0.0F);
		this.playSound(ModSoundEvents.SHOTGUN_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.world.spawnEntity(ShotgunShellEntity);
	}

	protected ShotgunShellEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
		return MarauderEntity.createArrowProjectile(this, arrow, damageModifier);
	}

	public boolean canUseRangedWeapon(Item weapon) {
		return weapon == DoomItems.SG;
	}

	public static ShotgunShellEntity createArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
		ShellAmmo arrowItem = (ShellAmmo) ((ShellAmmo) (stack.getItem() instanceof ShellAmmo ? stack.getItem()
				: DoomItems.SHOTGUN_SHELLS));
		ShotgunShellEntity persistentProjectileEntity = arrowItem.createArrow(entity.world, stack, entity, true);
		persistentProjectileEntity.applyEnchantmentEffects(entity, damageModifier);
		persistentProjectileEntity.setDamage(config.marauder_ssgdamage);
		return persistentProjectileEntity;
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.updateAttackType();
	}

	public void equipStack(EquipmentSlot slot, ItemStack stack) {
		super.equipStack(slot, stack);
		if (!this.world.isClient) {
			this.updateAttackType();
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ZOMBIEMAN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ZOMBIEMAN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ZOMBIEMAN_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public int tickTimer() {
		return age;
	}
}