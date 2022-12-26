package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.SplittableRandom;

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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class IconofsinEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(IconofsinEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
			BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true)
					.setCreateWorldFog(true);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public IconofsinEntity(EntityType<IconofsinEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && this.entityData.get(DEATH_STATE) == 0)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) == 0)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) && this.entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (event.isMoving() && this.entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_nohelmet"));
			if (this.entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle_nohelmet"));
			if (!event.isMoving() && this.hurtMarked && this.entityData.get(DEATH_STATE) == 1)
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle_nohelmet"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (this.level.isClientSide())
					this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(),
							DoomSounds.CYBERDEMON_STEP.get(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("shooting", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 1 && this.entityData.get(DEATH_STATE) == 0
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("summoned", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 2 && this.entityData.get(DEATH_STATE) == 1
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("summoned_nohelmet", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 3 && this.entityData.get(DEATH_STATE) == 0
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("slam", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 4 && this.entityData.get(DEATH_STATE) == 1
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("slam_nohelmet", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 5 && this.entityData.get(DEATH_STATE) == 0
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("stomp", LoopType.PLAY_ONCE));
			if (this.entityData.get(STATE) == 6 && this.entityData.get(DEATH_STATE) == 1
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("stomp_nohelmet", LoopType.PLAY_ONCE));
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
	protected boolean canRide(Entity p_184228_1_) {
		return false;
	}

	@Override
	public void knockback(double p_147241_, double p_147242_, double p_147243_) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(4,
				new IconAttackGoal(this,
						new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(DoomConfig.SERVER.icon_melee_damage.get().floatValue()
										+ (this.entityData.get(DEATH_STATE) == 1
												? DoomConfig.SERVER.icon_phaseone_damage_boos.get().floatValue()
												: 0))
								.setSound(SoundEvents.FIRECHARGE_USE, 1.0F,
										1.4F + this.getRandom().nextFloat() * 0.35F),
						1.1D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	public void spawnWave(int WaveAmount, LivingEntity entity) {
		RandomSource rand = this.getRandom();
		List<? extends String> waveEntries = DoomConfig.SERVER.icon_wave_entries.get();
		SplittableRandom random = new SplittableRandom();
		for (int k = 1; k < WaveAmount; ++k) {
			int r = random.nextInt(-3, 3);
			for (int i = 0; i < 1; ++i) {
				int randomIndex = rand.nextInt(waveEntries.size());
				ResourceLocation randomElement1 = new ResourceLocation(waveEntries.get(randomIndex));
				EntityType<?> randomElement = BuiltInRegistries.ENTITY_TYPE.get(randomElement1);
				Entity waveentity = randomElement.create(level);
				waveentity.setPos(entity.getX() + r, entity.getY() + 0.5D, entity.getZ() + r);
				level.addFreshEntity(waveentity);
			}
		}
	}

	public void doDamage() {
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(64D, 64D, 64D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof LivingEntity) {
				e.hurt(DamageSource.indirectMobAttack(this, this.getTarget()),
						DoomConfig.SERVER.icon_melee_damage.get().floatValue() + (this.entityData.get(DEATH_STATE) == 1
								? DoomConfig.SERVER.motherdemon_phaseone_damage_boos.get().floatValue()
								: 0));
			}
		});
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
					DoomConfig.SERVER.icon_melee_damage.get().floatValue() + (this.entityData.get(DEATH_STATE) == 1
							? DoomConfig.SERVER.motherdemon_phaseone_damage_boos.get().floatValue()
							: 0));
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.icon_health.get())
				.add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.icon_melee_damage.get())
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1000.0D);
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
		return DoomSounds.ICON_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.ICON_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ICON_DEATH.get();
	}

	public ServerBossEvent getBossInfo() {
		return bossInfo;
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
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEATH_STATE, 0);
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
	public int getArmorValue() {
		return this.entityData.get(DEATH_STATE) == 1 ? 0 : (int) ((getHealth() / getMaxHealth()) * 100);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		++this.tickCount;
		if (!this.level.isClientSide) {
			if (this.entityData.get(DEATH_STATE) == 0) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
			} else if (this.entityData.get(DEATH_STATE) == 1) {
				this.removeEffect(MobEffects.DAMAGE_BOOST);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
				this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10000000, 1));
			}
			if (!this.level.dimensionType().respawnAnchorWorks()) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10000000, 3));
				if (this.tickCount % 2400 == 0) {
					this.heal(40F);
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(64D, 64D, 64D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof IconofsinEntity && e.tickCount < 1) {
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
	public boolean hurt(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE
				? false
				: super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		boolean bl = target.hurt(DamageSource.mobAttack(this),
				(float) DoomConfig.SERVER.icon_melee_damage.get().floatValue() + (this.entityData.get(DEATH_STATE) == 1
						? DoomConfig.SERVER.icon_phaseone_damage_boos.get().floatValue()
						: 0));
		if (bl) {
			this.level.explode(this, target.getX(), target.getY(), target.getZ(), 3.0F, false,
					Level.ExplosionInteraction.BLOCK);
			this.doEnchantDamageEffects(this, target);
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