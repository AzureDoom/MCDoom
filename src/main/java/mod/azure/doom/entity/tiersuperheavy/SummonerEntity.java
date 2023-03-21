package mod.azure.doom.entity.tiersuperheavy;

import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SummonerEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private int targetChangeTime;
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SummonerEntity.class, EntityDataSerializers.INT);

	public SummonerEntity(EntityType<SummonerEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_SWOOP, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("summon", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.ARCHVILE_SCREAM, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
		goalSelector.addGoal(4, new SummonerEntity.AttackGoal(this));
		targetSelector.addGoal(1, new SummonerEntity.FindPlayerGoal(this, this::isAngryAt));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	static class AttackGoal extends Goal {
		private final SummonerEntity entity;
		public int cooldown;
		private int seeTime;
		private boolean strafingClockwise;
		private boolean strafingBackwards;
		private int strafingTime = -1;
		private final float maxAttackDistance = 20;
		private final int strafeTicks = 20;

		public AttackGoal(SummonerEntity ghast) {
			entity = ghast;
		}

		@Override
		public boolean canUse() {
			return entity.getTarget() != null;
		}

		@Override
		public void start() {
			super.start();
			entity.setAggressive(true);
			cooldown = 0;
			entity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			entity.setAggressive(false);
			entity.setAttackingState(0);
			seeTime = 0;
		}

		@Override
		public void tick() {
			final LivingEntity livingentity = entity.getTarget();
			++cooldown;
			final double distanceToTargetSq = entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			final boolean inLineOfSight = entity.getSensing().hasLineOfSight(livingentity);
			if (inLineOfSight != seeTime > 0) {
				seeTime = 0;
			}

			if (inLineOfSight) {
				++seeTime;
			} else {
				--seeTime;
			}

			if (distanceToTargetSq <= maxAttackDistance && seeTime >= 20) {
				entity.getNavigation().stop();
				++strafingTime;
			} else {
				entity.getNavigation().moveTo(livingentity, 0.95F);
				entity.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
				strafingTime = -1;
			}

			if (strafingTime >= strafeTicks) {
				if (entity.getRandom().nextFloat() < 0.3D) {
					strafingClockwise = !strafingClockwise;
				}

				if (entity.getRandom().nextFloat() < 0.3D) {
					strafingBackwards = !strafingBackwards;
				}

				strafingTime = 0;
			}

			if (strafingTime > -1) {
				if (distanceToTargetSq > maxAttackDistance * 0.75F) {
					strafingBackwards = false;
				} else if (distanceToTargetSq < maxAttackDistance * 0.25F) {
					strafingBackwards = true;
				}

				entity.getMoveControl().strafe(strafingBackwards ? -0.5F : 0.5F, strafingClockwise ? 0.5F : -0.5F);
				entity.lookAt(livingentity, 30.0F, 30.0F);
			} else {
				entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			}
			if (cooldown == 40) {
				if (!entity.level.isClientSide) {
					final AABB aabb = new AABB(entity.blockPosition()).inflate(64D);
					final int i = entity.level.getEntities(EntityTypeTest.forClass(DemonEntity.class), aabb, Entity::isAlive).size();
					final double d = Math.min(livingentity.getY(), entity.getY());
					final double e = Math.max(livingentity.getY(), entity.getY()) + 1.0D;
					final float f = (float) Mth.atan2(livingentity.getZ() - entity.getZ(), livingentity.getX() - entity.getX());
					int j;
					final SplittableRandom random = new SplittableRandom();
					final int r = random.nextInt(0, 40);
					if (r >= 17) {
						for (j = 0; j < 16; ++j) {
							final double l1 = 1.25D * (j + 1);
							entity.spawnFangs(entity.getX() + Mth.cos(f) * l1, entity.getZ() + Mth.sin(f) * l1, d, e, f, 32);
						}
					} else if (i <= 15)
						entity.spawnWave();
				}
				entity.setAttackingState(1);
			}
			if (cooldown >= 60) {
				entity.setAttackingState(0);
				cooldown = -5;
			}
			entity.lookAt(livingentity, 30.0F, 30.0F);
		}
	}

	public void spawnWave() {
		final List<EntityType<?>> givenList = Arrays.asList(DoomEntities.IMP, DoomEntities.LOST_SOUL, DoomEntities.IMP_STONE);
		final int r = random.nextInt(-3, 3);

		for (int k = 1; k < 5; ++k) {
			for (int i = 0; i < 1; i++) {
				final int randomIndex = random.nextInt(givenList.size());
				final EntityType<?> randomElement = givenList.get(randomIndex);
				final Entity fireballentity = randomElement.create(level);
				fireballentity.moveTo(this.getX() + r, this.getY() + 0.5D, this.getZ() + r, 0, 0);
				level.addFreshEntity(fireballentity);
			}
		}
	}

	static class FindPlayerGoal extends NearestAttackableTargetGoal<Player> {
		private final SummonerEntity enderman;
		/** The player */
		private Player player;
		private int aggroTime;
		private int teleportTime;
		private final TargetingConditions startAggroTargetConditions;
		private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();

		public FindPlayerGoal(SummonerEntity p_i241912_1_, @Nullable Predicate<LivingEntity> p_i241912_2_) {
			super(p_i241912_1_, Player.class, 10, false, false, p_i241912_2_);
			enderman = p_i241912_1_;
			startAggroTargetConditions = TargetingConditions.forCombat().range(getFollowDistance()).selector(p_32578_ -> p_i241912_1_.isLookingAtMe((Player) p_32578_));
		}

		@Override
		public boolean canUse() {
			player = enderman.level.getNearestPlayer(startAggroTargetConditions, enderman);
			return player != null;
		}

		@Override
		public void start() {
			aggroTime = 5;
			teleportTime = 0;
		}

		@Override
		public void stop() {
			player = null;
			super.stop();
		}

		@Override
		public boolean canContinueToUse() {
			if (player != null) {
				if (!enderman.isLookingAtMe(player)) {
					return false;
				} else {
					enderman.lookAt(player, 10.0F, 10.0F);
					return true;
				}
			} else {
				return target != null && continueAggroTargetConditions.test(enderman, target) ? true : super.canContinueToUse();
			}
		}

		@Override
		public void tick() {
			if (enderman.getTarget() == null) {
				super.setTarget((LivingEntity) null);
			}

			if (player != null) {
				if (--aggroTime <= 0) {
					target = player;
					player = null;
					super.start();
				}
			} else {
				if (target != null && !enderman.isPassenger()) {
					if (enderman.isLookingAtMe((Player) target) || (target.distanceToSqr(enderman) > 256.0D && teleportTime++ >= 30 && enderman.teleportTowards(target))) {

						teleportTime = 0;
					}
				}

				super.tick();
			}

		}
	}

	private boolean teleportTowards(Entity p_70816_1_) {
		Vec3 vec3 = new Vec3(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(), this.getZ() - p_70816_1_.getZ());
		vec3 = vec3.normalize();
		final double d1 = this.getX() + (random.nextDouble() - 0.5D) * 8.0D - vec3.x * 10.0D;
		final double d2 = this.getY() + (random.nextInt(16) - 8) - vec3.y * 10.0D;
		final double d3 = this.getZ() + (random.nextDouble() - 0.5D) * 8.0D - vec3.z * 10.0D;
		return teleport(d1, d2, d3);
	}

	private boolean isLookingAtMe(Player player) {
		final Vec3 vector3d = player.getViewVector(1.0F).normalize();
		Vec3 vector3d1 = new Vec3(this.getX() - player.getX(), getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
		final double d0 = vector3d1.length();
		vector3d1 = vector3d1.normalize();
		final double d1 = vector3d.dot(vector3d1);
		return d1 > 1.0D - 0.025D / d0 ? player.hasLineOfSight(this) : false;
	}

	@Override
	protected void customServerAiStep() {
		if (level.isDay() && tickCount >= targetChangeTime + 600) {
			final float f = getLightLevelDependentMagicValue();
			if (f > 0.5F && level.canSeeSky(blockPosition()) && random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				setTarget((LivingEntity) null);
			}
		}

		super.customServerAiStep();
	}

	protected boolean teleportRandomly() {
		if (!level.isClientSide() && isAlive()) {
			final double d0 = this.getX() + (random.nextDouble() - 0.5D) * 10.0D;
			final double d1 = this.getY() + (random.nextInt(64) - 10);
			final double d2 = this.getZ() + (random.nextDouble() - 0.5D) * 10.0D;
			return teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleport(double x, double y, double z) {
		final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

		while (blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		final BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
		final boolean flag = blockstate.getMaterial().blocksMotion();
		final boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			final boolean flag2 = randomTeleport(x, y, z, true);

			return flag2;
		} else {
			return false;
		}
	}

	public void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
		BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
		boolean flag = false;
		double d0 = 0.0D;
		do {
			final BlockPos blockpos1 = blockpos.below();
			final BlockState blockstate = level.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
				if (!level.isEmptyBlock(blockpos)) {
					final BlockState blockstate1 = level.getBlockState(blockpos);
					final VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}
				flag = true;
				break;
			}
			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(p_190876_5_) - 1);

		if (flag) {
			final DoomFireEntity fang = new DoomFireEntity(level, p_190876_1_, blockpos.getY() + d0, p_190876_3_, p_190876_9_, 1, this, DoomConfig.summoner_ranged_damage);
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			level.addFreshEntity(fang);
		}
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 30) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setVariant(compound.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());
	}

	public int getVariant() {
		return Mth.clamp(entityData.get(VARIANT), 1, 2);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		setVariant(random.nextInt());
		return spawnDataIn;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.summoner_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected void updateControlFlags() {
		final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
		goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
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