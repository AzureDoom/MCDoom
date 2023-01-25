package mod.azure.doom.entity.tierboss;

import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;

public class MotherDemonEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(MotherDemonEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
			BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true)
					.setCreateWorldFog(true);

	public MotherDemonEntity(EntityType<MotherDemonEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) == 0)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("moving"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("shooting", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("fire", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 80 && this.entityData.get(DEATH_STATE) == 0) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(1);
			this.deathTime = 0;
		}
		if (this.deathTime == 40 && this.entityData.get(DEATH_STATE) == 1) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public int getDeathState() {
		return this.entityData.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		this.entityData.set(DEATH_STATE, state);
	}

	@Override
	public void die(DamageSource source) {
		if (!this.level.isClientSide) {
			if (source == DamageSource.OUT_OF_WORLD) {
				this.setDeathState(1);
			}
			if (this.entityData.get(DEATH_STATE) == 0) {
				AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(),
						this.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				this.level.addFreshEntity(areaeffectcloudentity);
				this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
				this.setLastHurtMob(this.getLastHurtByMob());
				this.level.broadcastEntityEvent(this, (byte) 3);
			}
			if (this.entityData.get(DEATH_STATE) == 1) {
				super.die(source);
			}
		}
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, IronGolem.class, 8.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(1, new MotherDemonEntity.AttackGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(64D, 64D, 64D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof MotherDemonEntity && e.tickCount < 1) {
				e.remove(RemovalReason.KILLED);
			}
			if (e instanceof Player) {
				if (!((Player) e).isCreative())
					if (!((Player) e).isSpectator())
						this.setTarget((LivingEntity) e);
			}
		});
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 9.05F;
	}

	static class AttackGoal extends Goal {
		private final MotherDemonEntity parentEntity;
		protected int attackTimer = 0;

		public AttackGoal(MotherDemonEntity ghast) {
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
				Random rand = new Random();
				Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
				double d0 = Math.min(livingentity.getY(), livingentity.getY());
				double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
				double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * 2.0D);
				double d3 = livingentity.getY(0.5D) - (0.5D + this.parentEntity.getY(0.5D));
				double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * 2.0D);
				float f = (float) Mth.atan2(livingentity.getZ() - parentEntity.getZ(),
						livingentity.getX() - parentEntity.getX());
				CustomFireballEntity fireballentity = new CustomFireballEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.motherdemon_ranged_damage + (this.parentEntity.entityData.get(DEATH_STATE) == 1
								? DoomConfig.motherdemon_phaseone_damage_boos
								: 0));
				CustomFireballEntity fireballentity1 = new CustomFireballEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.motherdemon_ranged_damage + (this.parentEntity.entityData.get(DEATH_STATE) == 1
								? DoomConfig.motherdemon_phaseone_damage_boos
								: 0));
				CustomFireballEntity fireballentity2 = new CustomFireballEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.motherdemon_ranged_damage + (this.parentEntity.entityData.get(DEATH_STATE) == 1
								? DoomConfig.motherdemon_phaseone_damage_boos
								: 0));
				this.parentEntity.getNavigation().moveTo(livingentity, 1.5D);
				if (this.attackTimer == 15) {
					if (parentEntity.getHealth() <= (parentEntity.getMaxHealth() * 0.50)) {
						for (int l = 0; l < 32; ++l) {
							float f1 = f + (float) l * (float) Math.PI * 0.4F;
							for (int y = 0; y < 5; ++y) {
								parentEntity.spawnFlames(
										parentEntity.getX() + (double) Mth.cos(f1) * rand.nextDouble() * 11.5D,
										parentEntity.getZ() + (double) Mth.sin(f1) * rand.nextDouble() * 11.5D, d0, d1,
										f1, 0);
							}
							parentEntity.level.playLocalSound(this.parentEntity.getX(), this.parentEntity.getY(),
									this.parentEntity.getZ(), DoomSounds.MOTHER_ATTACK, SoundSource.HOSTILE, 1.0F, 1.0F,
									true);
							this.parentEntity.setAttackingState(2);
						}
						livingentity.setDeltaMovement(livingentity.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
						TentacleEntity lost_soul = DoomEntities.TENTACLE.create(world);
						lost_soul.moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 0, 0);
						world.addFreshEntity(lost_soul);
					} else {
						fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 1.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity);
						fireballentity1.setPos((this.parentEntity.getX() + 3) + vector3d.x * 1.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity1);
						fireballentity2.setPos((this.parentEntity.getX() - 3) + vector3d.x * 1.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity2);
						parentEntity.level.playLocalSound(this.parentEntity.getX(), this.parentEntity.getY(),
								this.parentEntity.getZ(), DoomSounds.MOTHER_ATTACK, SoundSource.HOSTILE, 1.0F, 1.0F,
								true);
						TentacleEntity lost_soul = DoomEntities.TENTACLE.create(world);
						lost_soul.moveTo(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 0, 0);
						world.addFreshEntity(lost_soul);
						this.parentEntity.setAttackingState(1);
					}
				}
				if (this.attackTimer >= 30) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -5;
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
			DoomFireEntity fang = new DoomFireEntity(this.level, x, (double) blockpos.getY() + d0, z, yaw, 1, this,
					DoomConfig.motherdemon_ranged_damage
							+ (this.entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos
									: 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.motherdemon_health).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.9f);
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.MOTHER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MOTHER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MOTHER_DEATH;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEATH_STATE, 0);
	}

	@Override
	public void setCustomName(Component name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
		this.setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", this.getDeathState());
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Override
	public void checkDespawn() {
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

}
