package mod.azure.doom.entity.tierfodder;

import java.util.EnumSet;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonFlightMoveControl;
import mod.azure.doom.entity.tierheavy.PainEntity;
import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LostSoulEntity extends DemonEntity implements Monster, IAnimatable, IAnimationTickable {

	protected static final TrackedData<Byte> VEX_FLAGS = DataTracker.registerData(LostSoulEntity.class,
			TrackedDataHandlerRegistry.BYTE);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(LostSoulEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public int explosionPower = 1;
	public int flameTimer;
	@Nullable
	private BlockPos bounds;

	public LostSoulEntity(EntityType<? extends LostSoulEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveControl = new DemonFlightMoveControl(this, 90, true);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<LostSoulEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 5) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
			if (!world.isClient) {
				this.explode();
			}
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VEX_FLAGS, (byte) 0);
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

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	public boolean isClimbing() {
		return true;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.lost_soul_health)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D).add(EntityAttributes.HORSE_JUMP_STRENGTH, 2.0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.lost_soul_melee_damage);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(7, new LostSoulEntity.LookAtTargetGoal(this));
		this.goalSelector.add(4, new LostSoulEntity.ChargeTargetGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public static boolean canSpawn(EntityType<PainEntity> type, WorldAccess world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0
				&& canMobSpawn(type, world, spawnReason, pos, random);
	}

	private boolean areFlagsSet(int mask) {
		int i = (Byte) this.dataTracker.get(VEX_FLAGS);
		return (i & mask) != 0;
	}

	private void setVexFlag(int mask, boolean value) {
		int i = (Byte) this.dataTracker.get(VEX_FLAGS);
		if (value) {
			i = i | mask;
		} else {
			i = i & ~mask;
		}

		this.dataTracker.set(VEX_FLAGS, (byte) (i & 255));
	}

	public boolean isCharging() {
		return this.areFlagsSet(1);
	}

	public void setCharging(boolean charging) {
		this.setVexFlag(1, charging);
	}

	@Nullable
	public BlockPos getBounds() {
		return this.bounds;
	}

	class ChargeTargetGoal extends Goal {
		public int attackTimer;
		private final LostSoulEntity parentEntity;

		public ChargeTargetGoal(LostSoulEntity ghast) {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
			this.parentEntity = ghast;
		}

		public boolean canStart() {
			return parentEntity.getTarget() != null;
		}

		public boolean shouldContinue() {
			return parentEntity.getTarget() != null && parentEntity.getTarget().isAlive();
		}

		public void start() {
			LivingEntity livingEntity = LostSoulEntity.this.getTarget();
			Vec3d vec3d = livingEntity.getCameraPosVec(1.0F);
			LostSoulEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 4.0D);
			LostSoulEntity.this.setCharging(true);
			LostSoulEntity.this.playSound(ModSoundEvents.LOST_SOUL_AMBIENT, 1.0F, 1.0F);
			this.attackTimer = 0;
		}

		public void stop() {
			LostSoulEntity.this.setCharging(false);
		}

		public void tick() {
			LivingEntity livingentity = parentEntity.getTarget();
			++this.attackTimer;
			parentEntity.setCharging(false);
			Vec3d vec3d = livingentity.getCameraPosVec(1.0F);
			parentEntity.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
			if (this.parentEntity.getBoundingBox().expand(0.20000000298023224D)
					.intersects(livingentity.getBoundingBox())) {
				this.parentEntity.tryAttack(livingentity);
			}
			this.attackTimer = Math.max(this.attackTimer - 0, 0);
		}
	}

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 8;
	}

	static class LookAtTargetGoal extends Goal {
		private final LostSoulEntity ghast;

		public LookAtTargetGoal(LostSoulEntity ghast) {
			this.ghast = ghast;
			this.setControls(EnumSet.of(Goal.Control.LOOK));
		}

		public boolean canStart() {
			return true;
		}

		public void tick() {
			if (this.ghast.getTarget() == null) {
				Vec3d vec3d = this.ghast.getVelocity();
				this.ghast.yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
				this.ghast.bodyYaw = this.ghast.yaw;
			} else {
				LivingEntity livingEntity = this.ghast.getTarget();
				if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0D) {
					double e = livingEntity.getX() - this.ghast.getX();
					double f = livingEntity.getZ() - this.ghast.getZ();
					this.ghast.yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
					this.ghast.bodyYaw = this.ghast.yaw;
				}
			}

		}
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F,
				Explosion.DestructionType.NONE);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.PAIN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.PAIN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.PAIN_DEATH;
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public int tickTimer() {
		return age;
	}

}