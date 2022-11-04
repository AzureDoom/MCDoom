package mod.azure.doom.entity.tierboss;

import java.util.EnumSet;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.KnockbackGoal;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ArchMakyrEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(ArchMakyrEntity.class,
			EntityDataSerializers.INT);
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ArchMakyrEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
			BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true)
					.setCreateWorldFog(true);

	public ArchMakyrEntity(EntityType<ArchMakyrEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new ArchMakyrEntity.MoveHelperController(this);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) < 5) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phaseone", EDefaultLoopTypes.PLAY_ONCE));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) == 5) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", EDefaultLoopTypes.PLAY_ONCE));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking_ranged", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking_aoe", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying_up", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<ArchMakyrEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<ArchMakyrEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void die(DamageSource source) {
		if (!this.level.isClientSide) {
			if (source == DamageSource.OUT_OF_WORLD) {
				this.setDeathState(5);
			}
			if (this.entityData.get(DEATH_STATE) > 5) {
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
			if (this.entityData.get(DEATH_STATE) == 5) {
				super.die(source);
			}
		}
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 80 && this.entityData.get(DEATH_STATE) == 0) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(1);
			this.deathTime = 0;
		}
		if (this.deathTime == 80 && this.entityData.get(DEATH_STATE) == 1) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(2);
			this.deathTime = 0;
		}
		if (this.deathTime == 80 && this.entityData.get(DEATH_STATE) == 2) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(3);
			this.deathTime = 0;
		}
		if (this.deathTime == 80 && this.entityData.get(DEATH_STATE) == 3) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(4);
			this.deathTime = 0;
		}
		if (this.deathTime == 80 && this.entityData.get(DEATH_STATE) == 4) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(5);
			this.deathTime = 0;
		}
		if (this.deathTime == 40 && this.entityData.get(DEATH_STATE) == 5) {
			this.level.broadcastEntityEvent(this, (byte) 60);
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
	public int getMaxFallDistance() {
		return 99;
	}

	@Override
	protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
		return 0;
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
	protected boolean canRide(Entity p_184228_1_) {
		return false;
	}

	@Override
	public int getArmorValue() {
		return 15;
	}

	protected PathNavigation createNavigation(Level worldIn) {
		FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

	static class MoveHelperController extends MoveControl {
		private final ArchMakyrEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(ArchMakyrEntity ghast) {
			super(ghast);
			this.parentEntity = ghast;
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
					Vec3 vector3d = new Vec3(this.wantedX - this.parentEntity.getX(),
							this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
					double d0 = vector3d.length();
					vector3d = vector3d.normalize();
					if (this.canReach(vector3d, Mth.ceil(d0))) {
						this.parentEntity
								.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
					} else {
						this.operation = MoveControl.Operation.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
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

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, IronGolem.class, 8.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(7, new ArchMakyrEntity.LookAroundGoal(this));
		this.goalSelector.addGoal(4, new RangedStrafeAttackGoal(this, new FireballAttack(this, true)
				.setProjectileOriginOffset(0.8, 0.4, 0.8)
				.setDamage(DoomConfig.SERVER.archmaykr_ranged_damage.get().floatValue()
						+ (this.entityData.get(DEATH_STATE) == 1
								? DoomConfig.SERVER.archmaykr_phaseone_damage_boost.get().floatValue()
								: this.entityData.get(DEATH_STATE) == 2
										? DoomConfig.SERVER.archmaykr_phasetwo_damage_boost.get().floatValue()
										: this.entityData.get(DEATH_STATE) == 3
												? DoomConfig.SERVER.archmaykr_phasethree_damage_boost.get().floatValue()
												: this.entityData.get(DEATH_STATE) == 4
														? DoomConfig.SERVER.archmaykr_phasefour_damage_boost.get()
																.floatValue()
														: 0)),
				1.0D, 1));
		this.targetSelector.addGoal(4, new KnockbackGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	static class LookAroundGoal extends Goal {
		private final ArchMakyrEntity parentEntity;

		public LookAroundGoal(ArchMakyrEntity ghast) {
			this.parentEntity = ghast;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.parentEntity.getTarget() == null) {
				Vec3 vec3d = this.parentEntity.getDeltaMovement();
				this.parentEntity.yRot = -((float) Mth.atan2(vec3d.x, vec3d.z)) * (180F / (float) Math.PI);
				this.parentEntity.yBodyRot = this.parentEntity.yRot;
			} else {
				LivingEntity livingentity = this.parentEntity.getTarget();
				if (livingentity.distanceToSqr(this.parentEntity) < 4096.0D) {
					double d1 = livingentity.getX() - this.parentEntity.getX();
					double d2 = livingentity.getZ() - this.parentEntity.getZ();
					this.parentEntity.yRot = -((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.parentEntity.yBodyRot = this.parentEntity.yRot;
				}
			}

		}
	}

	public ServerBossEvent getBossInfo() {
		return bossInfo;
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(DEATH_STATE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setVariant(compound.getInt("Variant"));
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
		this.setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", this.getDeathState());
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
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.archmaykr_health.get())
				.add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.55D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6f)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.5F;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setVariant(this.random.nextInt());
		return spawnDataIn;
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
	protected SoundEvent getAmbientSound() {
		return DoomSounds.MAKYR_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.MAKYR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.MAKYR_DEATH.get();
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
	public boolean isMaxGroupSizeReached(int p_204209_1_) {
		return this.isAlive() ? true : super.isMaxGroupSizeReached(p_204209_1_);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(64D, 64D, 64D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e.isAddedToWorld() && e instanceof ArchMakyrEntity && e.tickCount < 1) {
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
	public int tickTimer() {
		return tickCount;
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
					DoomConfig.SERVER.archmaykr_ranged_damage.get().floatValue()
							+ (this.entityData.get(DEATH_STATE) == 1
									? DoomConfig.SERVER.archmaykr_phaseone_damage_boost.get().floatValue()
									: this.entityData.get(DEATH_STATE) == 2
											? DoomConfig.SERVER.archmaykr_phasetwo_damage_boost.get().floatValue()
											: this.entityData.get(DEATH_STATE) == 3
													? DoomConfig.SERVER.archmaykr_phasethree_damage_boost.get()
															.floatValue()
													: this.entityData.get(DEATH_STATE) == 4
															? DoomConfig.SERVER.archmaykr_phasefour_damage_boost.get()
																	.floatValue()
															: 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Override
	public void checkDespawn() {
	}

	@Override
	public void knockback(double p_147241_, double p_147242_, double p_147243_) {
		super.knockback(0, 0, 0);
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

}
