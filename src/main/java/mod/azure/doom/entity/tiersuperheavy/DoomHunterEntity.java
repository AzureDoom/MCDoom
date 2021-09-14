package mod.azure.doom.entity.tiersuperheavy;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.config.DoomConfig;

import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DoomHunterEntity extends DemonEntity implements IAnimatable {

	
	public int flameTimer;

	public DoomHunterEntity(EntityType<DoomHunterEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && this.getHealth() > (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && this.getHealth() < (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_nosled", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.getHealth() < (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_nosled", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("rockets", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flamethrower", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("chainsaw", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 4 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flamethrower_nosled", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 5 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("chainsaw_nosled", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<DoomHunterEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<DoomHunterEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(4, new DoomHunterEntity.AttackGoal(this));
		this.goalSelector.addGoal(4, new DoomHunterEntity.DemonAttackGoal(this, 1.0D, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	public class DemonAttackGoal extends MeleeAttackGoal {
		private final DoomHunterEntity entity;
		private final double speedModifier;
		private int ticksUntilNextAttack;
		private int ticksUntilNextPathRecalculation;
		private final boolean followingTargetEvenIfNotSeen;
		private double pathedTargetX;
		private double pathedTargetY;
		private double pathedTargetZ;
		private int failedPathFindingPenalty = 0;
		private boolean canPenalize = false;

		public DemonAttackGoal(DoomHunterEntity zombieIn, double speedIn, boolean longMemoryIn) {
			super(zombieIn, speedIn, longMemoryIn);
			this.entity = zombieIn;
			this.speedModifier = speedIn;
			this.followingTargetEvenIfNotSeen = longMemoryIn;
		}

		public void start() {
			super.start();
		}

		public boolean canUse() {
			return super.canUse();
		}

		public void stop() {
			super.stop();
			this.entity.setAggressive(false);
			this.entity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
				double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
				this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
				if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity))
						&& this.ticksUntilNextPathRecalculation <= 0
						&& (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
								|| livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY,
										this.pathedTargetZ) >= 1.0D
								|| this.mob.getRandom().nextFloat() < 0.05F)) {
					this.pathedTargetX = livingentity.getX();
					this.pathedTargetY = livingentity.getY();
					this.pathedTargetZ = livingentity.getZ();
					this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
					if (this.canPenalize) {
						this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
						if (this.mob.getNavigation().getPath() != null) {
							net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation()
									.getPath().getEndNode();
							if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y,
									finalPathPoint.z) < 1)
								failedPathFindingPenalty = 0;
							else
								failedPathFindingPenalty += 10;
						} else {
							failedPathFindingPenalty += 10;
						}
					}
					if (d0 > 1024.0D) {
						this.ticksUntilNextPathRecalculation += 10;
					} else if (d0 > 256.0D) {
						this.ticksUntilNextPathRecalculation += 5;
					}

					if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
						this.ticksUntilNextPathRecalculation += 15;
					}
				}

				this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
				this.checkAndPerformAttack(livingentity, d0);
			}
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity livingentity, double squaredDistance) {
			double d0 = this.getAttackReachSqr(livingentity);
			if (squaredDistance <= d0 && this.getTicksUntilNextAttack() <= 0) {
				this.resetAttackCooldown();
				this.entity.setAttackingState(entity.getHealth() < (entity.getMaxHealth() * 0.50) ? 5 : 3);
				this.mob.doHurtTarget(livingentity);
			}
		}

		@Override
		protected int getAttackInterval() {
			return 50;
		}

		@Override
		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return (double) (this.mob.getBbWidth() * 1.0F * this.mob.getBbWidth() * 1.0F + attackTarget.getBbWidth());
		}
	}

	static class AttackGoal extends Goal {
		private final DoomHunterEntity parentEntity;
		protected int attackTimer = 0;

		public AttackGoal(DoomHunterEntity ghast) {
			this.parentEntity = ghast;
		}

		public boolean canUse() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			super.start();
			this.parentEntity.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAggressive(false);
			this.parentEntity.setAttackingState(0);
			this.attackTimer = -1;
		}

		public void tick() {
			LivingEntity livingentity = this.parentEntity.getTarget();
			if (this.parentEntity.hasLineOfSight(livingentity)) {
				Level world = this.parentEntity.level;
				++this.attackTimer;
				Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
				double d0 = Math.min(livingentity.getY(), livingentity.getY());
				double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
				double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * 2.0D);
				double d3 = livingentity.getY(0.5D) - (0.5D + this.parentEntity.getY(0.5D));
				double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * 2.0D);
				float f = (float) Mth.atan2(livingentity.getZ() - parentEntity.getZ(),
						livingentity.getX() - parentEntity.getX());
				RocketMobEntity fireballentity = new RocketMobEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.SERVER.doomhunter_ranged_damage.get().floatValue());
				if (this.attackTimer == 15) {
					if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
						for (int l = 0; l < 16; ++l) {
							double d5 = 1.25D * (double) (l + 1);
							int j = 1 * l;
							parentEntity.spawnFlames(parentEntity.getX() + (double) Mth.cos(f) * d5,
									parentEntity.getZ() + (double) Mth.sin(f) * d5, d0, d1, f, j);
							if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
								this.parentEntity.setAttackingState(4);
							} else {
								this.parentEntity.setAttackingState(2);
							}
						}
					}
					if (parentEntity.getHealth() > (parentEntity.getMaxHealth() * 0.50)) {
						fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 2.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 2.0D);
						world.addFreshEntity(fireballentity);
						this.parentEntity.setAttackingState(1);
					}
				}
				if (this.attackTimer == 25) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -150;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.lookAt(livingentity, 30.0F, 30.0F);
		}

	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockpos = new BlockPos(x, y, z);
		boolean flag = false;
		double d0 = 0.0D;
		do {
			BlockPos blockpos1 = blockpos.below();
			BlockState blockstate = this.level.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(this.level, blockpos1, Direction.UP)) {
				if (!this.level.isEmptyBlock(blockpos)) {
					BlockState blockstate1 = this.level.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate1.getCollisionShape(this.level, blockpos);
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
			DoomFireEntity fang = new DoomFireEntity(this.level, x, (double) blockpos.getY() + d0, z, yaw, 1, this);
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.doomhunter_health.get()).add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.doomhunter_melee_damage.get())
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected int getExperienceReward(Player player) {
		return super.getExperienceReward(player);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 6.05F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.DOOMHUNTER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.DOOMHUNTER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.DOOMHUNTER_DEATH.get();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public int getArmorValue() {
		float health = this.getHealth();
		return (health < (this.getMaxHealth() * 0.90) && health >= (this.getMaxHealth() * 0.85) ? 8
				: health < (this.getMaxHealth() * 0.80) && health >= (this.getMaxHealth() * 0.75) ? 6
						: health < (this.getMaxHealth() * 0.70) && health >= (this.getMaxHealth() * 0.65) ? 4
								: health < (this.getMaxHealth() * 0.60) && health >= (this.getMaxHealth() * 0.55) ? 4
										: health < (this.getMaxHealth() * 0.55)
												&& health >= (this.getMaxHealth() * 0.50) ? 2
														: health < (this.getMaxHealth() * 0.50) ? 0 : 10);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 8;
		if (this.getHealth() < 75.0D) {
			if (!this.level.isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
				this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10000000, 1));
			}
		}
	}

	public int getFlameTimer() {
		return flameTimer;
	}

}