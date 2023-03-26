package mod.azure.doom.entity.tierboss;

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
import mod.azure.doom.entity.ai.goal.RangedStrafeGladiatorAttackGoal;
import mod.azure.doom.entity.attack.AbstractDoubleRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class GladiatorEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(GladiatorEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> TEXTURE = SynchedEntityData.defineId(GladiatorEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20).setDarkenScreen(false).setCreateWorldFog(false);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public GladiatorEntity(EntityType<? extends DemonEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (entityData.get(DEATH_STATE) == 0 && event.isMoving() && entityData.get(STATE) < 1) {
				event.getController().setAnimationSpeed(1.5);
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_phaseone"));
			}
			if (entityData.get(DEATH_STATE) == 0 && (dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if (entityData.get(DEATH_STATE) == 1 && (dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phasetwo"));
			if (entityData.get(DEATH_STATE) == 0 && entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("shield_plant"));
			if (entityData.get(DEATH_STATE) == 1 && event.isMoving()) {
				event.getController().setAnimationSpeed(1.5);
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_phasetwo"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop(entityData.get(DEATH_STATE) == 0 ? "idle_phaseone" : "idle_phasetwo"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(DEATH_STATE) == 0 && entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone", LoopType.PLAY_ONCE));
			if (entityData.get(DEATH_STATE) == 0 && entityData.get(STATE) == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone2", LoopType.PLAY_ONCE));
			if (entityData.get(DEATH_STATE) == 0 && entityData.get(STATE) == 4 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone3", LoopType.PLAY_ONCE));
			if (entityData.get(DEATH_STATE) == 1 && entityData.get(STATE) == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo", LoopType.PLAY_ONCE));
			if (entityData.get(DEATH_STATE) == 1 && entityData.get(STATE) == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo2", LoopType.PLAY_ONCE));
			if (entityData.get(DEATH_STATE) == 1 && entityData.get(STATE) == 4 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo2", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public void die(DamageSource source) {
		if (!level.isClientSide) {
			if (source == damageSources().outOfWorld())
				setDeathState(1);
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

	@Override
	protected boolean shouldDropLoot() {
		return true;
	}

	public int getDeathState() {
		return entityData.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		entityData.set(DEATH_STATE, state);
	}

	public int getTextureState() {
		return entityData.get(TEXTURE);
	}

	public void setTextureState(int state) {
		entityData.set(TEXTURE, state);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (hasCustomName()) {
			bossInfo.setName(getDisplayName());
		}
		setTextureState(compound.getInt("Texture"));
		setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", getDeathState());
		tag.putInt("Texture", getDeathState());
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DEATH_STATE, 0);
		entityData.define(TEXTURE, 0);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!level.isClientSide) {
			if (entityData.get(DEATH_STATE) == 0) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 0, false, false));
			} else {
				removeEffect(MobEffects.DAMAGE_RESISTANCE);
			}
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(4, new RangedStrafeGladiatorAttackGoal(this, new FireballAttack(this).setProjectileOriginOffset(0.8, 0.8, 0.8).setDamage(DoomConfig.gladiator_ranged_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.gladiator_phaseone_damage_boost : 0)).setSound(SoundEvents.FIRECHARGE_USE, 1.0F, 1.4F + getRandom().nextFloat() * 0.35F)));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}

	public class FireballAttack extends AbstractDoubleRangedAttack {

		public FireballAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction, double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(DemonEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(DoomSounds.BALLISTA_FIRING, 1, 1);
		}

		@Override
		public Projectile getProjectile(Level world, double d2, double d3, double d4) {
			return new CustomFireballEntity(world, parentEntity, d2, d3, d4, damage);

		}

		@Override
		public Projectile getProjectile2(Level world, double d2, double d3, double d4) {
			return new GladiatorMaceEntity(world, parentEntity, d2, d3, d4);
		}
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		level.broadcastEntityEvent(this, (byte) 4);
		final boolean bl = target.hurt(damageSources().mobAttack(this), (float) DoomConfig.gladiator_melee_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.gladiator_phaseone_damage_boost : 0));
		if (bl) {
			target.setDeltaMovement(target.getDeltaMovement().multiply(1.4f, 1.4f, 1.4f));
			doEnchantDamageEffects(this, target);
			level.explode(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false, Level.ExplosionInteraction.BLOCK);
			target.invulnerableTime = 0;
		}
		return true;
	}

	public boolean tryAttack1(Entity target) {
		level.broadcastEntityEvent(this, (byte) 4);
		final boolean bl = target.hurt(damageSources().mobAttack(this), (float) DoomConfig.gladiator_melee_damage + (entityData.get(DEATH_STATE) == 1 ? DoomConfig.gladiator_phaseone_damage_boost : 0));
		if (bl) {
			target.setDeltaMovement(target.getDeltaMovement().multiply(1.4f, 1.4f, 1.4f));
			doEnchantDamageEffects(this, target);
			level.explode(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false, Level.ExplosionInteraction.BLOCK);
			target.invulnerableTime = 0;
		}
		return true;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, DoomConfig.gladiator_health).add(Attributes.ATTACK_DAMAGE, DoomConfig.gladiator_melee_damage).add(Attributes.KNOCKBACK_RESISTANCE, 0.9f).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.BARON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.BARON_DEATH;
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
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
		getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof GladiatorEntity && e.tickCount < 1) {
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
	public boolean hurt(DamageSource source, float amount) {
		return getAttckingState() == 1 || getAttckingState() == 4 ? false : super.hurt(source, amount);
	}

}
