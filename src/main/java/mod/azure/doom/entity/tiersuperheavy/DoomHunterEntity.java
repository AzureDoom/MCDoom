package mod.azure.doom.entity.tiersuperheavy;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DoomHunterEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public DoomHunterEntity(EntityType<DoomHunterEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public int flameTimer;
	private AnimationFactory factory = new AnimationFactory(this);
	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(DoomHunterEntity.class,
			EntityDataSerializers.INT);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("sled_death", false));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && this.hurtDuration < 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
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
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		event.getController().setAnimationSpeed(0.5);
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<DoomHunterEntity> controller = new AnimationController<DoomHunterEntity>(this, "controller",
				0, this::predicate);
		controller.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("phasechange")) {
			if (this.level.isClientSide) {
				this.getCommandSenderWorld().playLocalSound(this.getX(), this.getY(), this.getZ(),
						DoomSounds.DOOMHUNTER_PHASECHANGE.get(), SoundSource.HOSTILE, 0.25F, 1.0F, true);
			}
		}
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
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
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
			this.parentEntity.setAttackingState(0);
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
						DoomConfig.SERVER.doomhunter_ranged_damage.get().floatValue()
								+ (this.parentEntity.entityData.get(DEATH_STATE) == 1
										? DoomConfig.SERVER.doomhunter_extra_phase_two_damage.get().floatValue()
										: 0));
				this.parentEntity.getNavigation().moveTo(livingentity,
						this.parentEntity.getDeathState() == 0 ? 0.75 : 1.0);
				if (this.attackTimer == 15) {
					if (this.parentEntity.distanceTo(livingentity) >= 3.0D) {
						if (this.parentEntity.entityData.get(DEATH_STATE) == 1) {
							for (int l = 0; l < 16; ++l) {
								double d5 = 1.25D * (double) (l + 1);
								int j = 1 * l;
								parentEntity.spawnFlames(parentEntity.getX() + (double) Mth.cos(f) * d5,
										parentEntity.getZ() + (double) Mth.sin(f) * d5, d0, d1, f, j);
								this.parentEntity.setAttackingState(2);
							}
						}
						if (this.parentEntity.entityData.get(DEATH_STATE) == 0) {
							fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 2.0D,
									this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 2.0D);
							world.addFreshEntity(fireballentity);
							this.parentEntity.setAttackingState(1);
						}
					} else {
						this.parentEntity.setAttackingState(3);
						this.parentEntity.doHurtTarget(livingentity);
					}
				}
				if (this.attackTimer == 35) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -15;
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
					DoomConfig.SERVER.doomhunter_ranged_damage.get().floatValue()
							+ (this.entityData.get(DEATH_STATE) == 1
									? DoomConfig.SERVER.doomhunter_extra_phase_two_damage.get().floatValue()
									: 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.doomhunter_health.get())
				.add(Attributes.FLYING_SPEED, 2.25D)
				.add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.doomhunter_melee_damage.get())
				.add(Attributes.MOVEMENT_SPEED, 0.55D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 6.05F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.DOOMHUNTER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.DOOMHUNTER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.DOOMHUNTER_DEATH.get();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 8;
		++this.tickCount;
		if (!this.level.isClientSide) {
			if (this.entityData.get(DEATH_STATE) == 0) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
			} else if (this.entityData.get(DEATH_STATE) == 1) {
				this.removeEffect(MobEffects.DAMAGE_BOOST);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
			}
		}
	}

	@Override
	public int getArmorValue() {
		return this.entityData.get(DEATH_STATE) == 1 ? 0 : (int) ((getHealth() / getMaxHealth()) * 100);
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
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", this.getDeathState());
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEATH_STATE, 0);
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Override
	public void checkDespawn() {
	}

}