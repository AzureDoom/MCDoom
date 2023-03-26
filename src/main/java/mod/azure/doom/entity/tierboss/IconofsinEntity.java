package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.SplittableRandom;

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
import mod.azure.doom.entity.ai.goal.IconAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
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
import net.minecraft.world.phys.shapes.VoxelShape;

public class IconofsinEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(IconofsinEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(true);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public IconofsinEntity(EntityType<IconofsinEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && entityData.get(DEATH_STATE) == 0)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) == 0)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if ((dead || getHealth() < 0.01 || isDeadOrDying()) && entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (event.isMoving() && entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_nohelmet"));
			if ((entityData.get(DEATH_STATE) == 1) || (!event.isMoving() && hurtMarked && entityData.get(DEATH_STATE) == 1))
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle_nohelmet"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.CYBERDEMON_STEP, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("shooting", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 1 && entityData.get(DEATH_STATE) == 0 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("summoned", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 2 && entityData.get(DEATH_STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("summoned_nohelmet", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 3 && entityData.get(DEATH_STATE) == 0 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("slam", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 4 && entityData.get(DEATH_STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("slam_nohelmet", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 5 && entityData.get(DEATH_STATE) == 0 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("stomp", LoopType.PLAY_ONCE));
			if (entityData.get(STATE) == 6 && entityData.get(DEATH_STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("stomp_nohelmet", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 80 && entityData.get(DEATH_STATE) == 0) {
			setHealth(getMaxHealth());
			setDeathState(1);
			deathTime = 0;
		}
		if (deathTime == 40 && entityData.get(DEATH_STATE) == 1) {
			remove(Entity.RemovalReason.KILLED);
			dropExperience();
		}
	}

	public int getDeathState() {
		return entityData.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		entityData.set(DEATH_STATE, state);
	}

	@Override
	public void die(DamageSource source) {
		if (!level.isClientSide) {
			if (source == damageSources().outOfWorld()) {
				setDeathState(1);
			}
			if (entityData.get(DEATH_STATE) == 0) {
				final AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(level, this.getX(), this.getY(), this.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				level.addFreshEntity(areaeffectcloudentity);
				goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
				setLastHurtMob(getLastHurtByMob());
				level.broadcastEntityEvent(this, (byte) 3);
			}
			if (entityData.get(DEATH_STATE) == 1) {
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
	protected boolean canRide(Entity p_184228_1_) {
		return false;
	}

	@Override
	public void knockback(double p_147241_, double p_147242_, double p_147243_) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(4, new IconAttackGoal(this, new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.8, 0.8).setDamage(DoomConfig.icon_melee_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.icon_phaseone_damage_boos : 0)).setSound(SoundEvents.FIRECHARGE_USE, 1.0F, 1.4F + getRandom().nextFloat() * 0.35F), 1.1D));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	public void spawnWave(int WaveAmount, LivingEntity entity) {
		final RandomSource rand = getRandom();
		final List<? extends String> waveEntries = DoomConfig.icon_wave_entries;
		final SplittableRandom random = new SplittableRandom();
		for (int k = 1; k < WaveAmount; ++k) {
			final int r = random.nextInt(-3, 3);
			for (int i = 0; i < 1; ++i) {
				final int randomIndex = rand.nextInt(waveEntries.size());
				final ResourceLocation randomElement1 = new ResourceLocation(waveEntries.get(randomIndex));
				final EntityType<?> randomElement = BuiltInRegistries.ENTITY_TYPE.get(randomElement1);
				final Entity waveentity = randomElement.create(level);
				waveentity.setPos(entity.getX() + r, entity.getY() + 0.5D, entity.getZ() + r);
				level.addFreshEntity(waveentity);
			}
		}
	}

	public void doDamage() {
		final AABB aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof LivingEntity) {
				e.hurt(damageSources().indirectMagic(this, getTarget()), DoomConfig.icon_melee_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
			}
		});
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockpos = BlockPos.containing(x, y, z);
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
		} while (blockpos.getY() >= Mth.floor(maxY) - 1);

		if (flag) {
			final DoomFireEntity fang = new DoomFireEntity(level, x, blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.icon_melee_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.icon_health).add(Attributes.ATTACK_DAMAGE, DoomConfig.icon_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1000.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 18.70F;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.ICON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.ICON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ICON_DEATH;
	}

	public ServerBossEvent getBossInfo() {
		return bossInfo;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DEATH_STATE, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (hasCustomName()) {
			bossInfo.setName(getDisplayName());
		}
		setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", getDeathState());
	}

	@Override
	public void setCustomName(Component name) {
		super.setCustomName(name);
		bossInfo.setName(getDisplayName());
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		bossInfo.setProgress(getHealth() / getMaxHealth());
	}

	@Override
	public int getArmorValue() {
		return entityData.get(DEATH_STATE) == 1 ? 0 : (int) (getHealth() / getMaxHealth() * 100);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		++tickCount;
		if (!level.isClientSide) {
			if (entityData.get(DEATH_STATE) == 0) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
			} else if (entityData.get(DEATH_STATE) == 1) {
				removeEffect(MobEffects.DAMAGE_BOOST);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
				this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10000000, 1));
			}
			if (!level.dimensionType().respawnAnchorWorks()) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10000000, 3));
				if (tickCount % 2400 == 0) {
					heal(40F);
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof IconofsinEntity && e.tickCount < 1) {
				e.remove(RemovalReason.KILLED);
			}
			if (e instanceof Player) {
				if (!((Player) e).isCreative())
					if (!((Player) e).isSpectator())
						setTarget((LivingEntity) e);
			}
		});
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == damageSources().inWall() || source == damageSources().onFire() || source == damageSources().inFire() ? false : super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		level.broadcastEntityEvent(this, (byte) 4);
		final boolean bl = target.hurt(damageSources().mobAttack(this), DoomConfig.icon_melee_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.icon_phaseone_damage_boos : 0));
		if (bl) {
			level.explode(this, target.getX(), target.getY(), target.getZ(), 3.0F, false, Level.ExplosionInteraction.BLOCK);
			doEnchantDamageEffects(this, target);
			target.invulnerableTime = 0;
		}
		return bl;
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

	@Override
	public boolean fireImmune() {
		return true;
	}
}