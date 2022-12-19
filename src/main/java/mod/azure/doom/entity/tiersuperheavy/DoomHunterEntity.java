package mod.azure.doom.entity.tiersuperheavy;

import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DoomHunterEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public int flameTimer;
	public static final TrackedData<Integer> DEATH_STATE = DataTracker.registerData(DoomHunterEntity.class,
			TrackedDataHandlerRegistry.INTEGER);

	public DoomHunterEntity(EntityType<? extends DoomHunterEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if ((this.dead || this.getHealth() < 0.01 || this.isDead()) && this.dataTracker.get(DEATH_STATE) == 1) 
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDead())) 
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("sled_death"));
			if (event.isMoving() && this.maxHurtTime < 0) 
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) 
				return event.setAndContinue(RawAnimation.begin().thenLoop("rockets"));
			if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) 
				return event.setAndContinue(RawAnimation.begin().thenLoop("flamethrower"));
			if (this.dataTracker.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) 
				return event.setAndContinue(RawAnimation.begin().thenLoop("chainsaw"));
			event.getController().setAnimationSpeed(0.5);
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("phasechange"))
				if (this.world.isClient)
					this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.DOOMHUNTER_PHASECHANGE,
							SoundCategory.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public static boolean spawning(EntityType<BaronEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(4, new DoomHunterEntity.AttackGoal(this));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	public boolean isClimbing() {
		return true;
	}

	static class AttackGoal extends Goal {
		private final DoomHunterEntity parentEntity;
		protected int cooldown = 0;

		public AttackGoal(DoomHunterEntity parentEntity) {
			this.parentEntity = parentEntity;
		}

		public boolean canStart() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			super.start();
			this.parentEntity.setAttacking(true);
			this.cooldown = -1;
			this.parentEntity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAttacking(false);
			this.parentEntity.setAttackingState(0);
			this.cooldown = -1;
		}

		public void tick() {
			LivingEntity livingEntity = this.parentEntity.getTarget();
			if (this.parentEntity.canSee(livingEntity)) {
				Vec3d vec3d = livingEntity.getRotationVec(1.0F);
				World world = this.parentEntity.world;
				++this.cooldown;
				double f = livingEntity.getX() - (this.parentEntity.getX() + vec3d.x * 2.0D);
				double g = livingEntity.getBodyY(0.5D) - (0.5D + this.parentEntity.getBodyY(0.5D));
				double h = livingEntity.getZ() - (this.parentEntity.getZ() + vec3d.z * 2.0D);
				RocketMobEntity fireballEntity = new RocketMobEntity(world, this.parentEntity, f, g, h,
						DoomConfig.doomhunter_ranged_damage + (this.parentEntity.dataTracker.get(DEATH_STATE) == 1
								? DoomConfig.doomhunter_extra_phase_two_damage
								: 0));
				double d = Math.min(livingEntity.getY(), parentEntity.getY());
				double e1 = Math.max(livingEntity.getY(), parentEntity.getY()) + 1.0D;
				float f2 = (float) MathHelper.atan2(livingEntity.getZ() - parentEntity.getZ(),
						livingEntity.getX() - parentEntity.getX());
				this.parentEntity.getNavigation().startMovingTo(livingEntity, 0.75);
				if (this.cooldown == 15) {
					if (this.parentEntity.distanceTo(livingEntity) >= 3.0D) {
						if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
							for (int l = 0; l < 16; ++l) {
								double l1 = 1.25D * (double) (l + 1);
								int m = 1 * l;
								parentEntity.spawnFlames(parentEntity.getX() + (double) MathHelper.cos(f2) * l1,
										parentEntity.getZ() + (double) MathHelper.sin(f2) * l1 + 0.5, d, e1, f2, m);
								this.parentEntity.setAttackingState(2);
							}
						}
						if (parentEntity.getHealth() > (parentEntity.getMaxHealth() * 0.50)) {
							this.parentEntity.setAttackingState(1);
							fireballEntity.updatePosition(this.parentEntity.getX() + vec3d.x * 2.0D,
									this.parentEntity.getBodyY(0.5D) + 0.5D, parentEntity.getZ() + vec3d.z * 2.0D);
							world.spawnEntity(fireballEntity);
						}
					} else {
						this.parentEntity.setAttackingState(3);
						this.parentEntity.tryAttack(livingEntity);
					}
				}
				if (this.cooldown >= 35) {
					this.parentEntity.setAttackingState(0);
					this.cooldown = -15;
				}
			} else if (this.cooldown > 0) {
				--this.cooldown;
			}
			this.parentEntity.lookAtEntity(livingEntity, 30.0F, 30.0F);
		}
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
					DoomConfig.doomhunter_ranged_damage
							+ (this.dataTracker.get(DEATH_STATE) == 1 ? DoomConfig.doomhunter_extra_phase_two_damage
									: 0));
			fang.setFireTicks(age);
			fang.isInvisible();
			this.world.spawnEntity(fang);
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.doomhunter_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, DoomConfig.doomhunter_melee_damage)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 2.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
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
		return 6.55F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.DOOMHUNTER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.DOOMHUNTER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.DOOMHUNTER_DEATH;
	}

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 8;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		++this.age;
		if (!this.world.isClient) {
			if (this.dataTracker.get(DEATH_STATE) == 0) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000000, 1));
			} else if (this.dataTracker.get(DEATH_STATE) == 1) {
				this.removeStatusEffect(StatusEffects.STRENGTH);
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10000000, 2));
			}
		}
	}

	@Override
	public int getArmor() {
		return this.dataTracker.get(DEATH_STATE) == 1 ? 0 : (int) ((getHealth() / getMaxHealth()) * 100);
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 80 && this.dataTracker.get(DEATH_STATE) == 0) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(1);
			this.deathTime = 0;
		}
		if (this.deathTime == 40 && this.dataTracker.get(DEATH_STATE) == 1) {
			this.world.sendEntityStatus(this, (byte) 60);
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public int getDeathState() {
		return this.dataTracker.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		this.dataTracker.set(DEATH_STATE, state);
	}

	@Override
	public void onDeath(DamageSource source) {
		if (!this.world.isClient) {
			if (source == DamageSource.OUT_OF_WORLD) {
				this.setDeathState(1);
			}
			if (this.dataTracker.get(DEATH_STATE) == 0) {
				AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getX(),
						this.getY(), this.getZ());
				areaeffectcloudentity.setParticleType(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				this.world.spawnEntity(areaeffectcloudentity);
				this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::stop);
				this.onAttacking(this.getAttacker());
				this.world.sendEntityStatus(this, (byte) 3);
			}
			if (this.dataTracker.get(DEATH_STATE) == 1) {
				super.onDeath(source);
			}
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setDeathState(tag.getInt("Phase"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Phase", this.getDeathState());
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DEATH_STATE, 0);
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Override
	public void checkDespawn() {
	}

	@Override
	public boolean isImmuneToExplosion() {
		return true;
	}

}