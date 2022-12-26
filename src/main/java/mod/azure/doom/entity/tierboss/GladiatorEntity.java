package mod.azure.doom.entity.tierboss;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RangedStrafeGladiatorAttackGoal;
import mod.azure.doom.entity.attack.AbstractDoubleRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.CustomFireballEntity;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GladiatorEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(GladiatorEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> TEXTURE = SynchedEntityData.defineId(GladiatorEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
			BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20)).setDarkenScreen(false)
					.setCreateWorldFog(false);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public GladiatorEntity(EntityType<? extends DemonEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (this.entityData.get(DEATH_STATE) == 0 && event.isMoving() && this.entityData.get(STATE) < 1) {
				event.getController().setAnimationSpeed(1.5);
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_phaseone"));
			}
			if (this.entityData.get(DEATH_STATE) == 0 && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if (this.entityData.get(DEATH_STATE) == 1 && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phasetwo"));
			if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 1
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("shield_plant"));
			if (this.entityData.get(DEATH_STATE) == 1 && event.isMoving()) {
				event.getController().setAnimationSpeed(1.5);
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_phasetwo"));
			}
			return event.setAndContinue(RawAnimation.begin()
					.thenLoop((this.entityData.get(DEATH_STATE) == 0 ? "idle_phaseone" : "idle_phasetwo")));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 2
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone", LoopType.PLAY_ONCE));
			if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 3
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone2", LoopType.PLAY_ONCE));
			if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 4
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone3", LoopType.PLAY_ONCE));
			if (this.entityData.get(DEATH_STATE) == 1 && this.entityData.get(STATE) == 2
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo", LoopType.PLAY_ONCE));
			if (this.entityData.get(DEATH_STATE) == 1 && this.entityData.get(STATE) == 3
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo2", LoopType.PLAY_ONCE));
			if (this.entityData.get(DEATH_STATE) == 1 && this.entityData.get(STATE) == 4
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo2", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void die(DamageSource source) {
		if (!this.level.isClientSide) {
			if (source == DamageSource.OUT_OF_WORLD)
				this.setDeathState(1);
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

	@Override
	protected boolean shouldDropLoot() {
		return true;
	}

	public int getDeathState() {
		return this.entityData.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		this.entityData.set(DEATH_STATE, state);
	}

	public int getTextureState() {
		return this.entityData.get(TEXTURE);
	}

	public void setTextureState(int state) {
		this.entityData.set(TEXTURE, state);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
		this.setTextureState(compound.getInt("Texture"));
		this.setDeathState(compound.getInt("Phase"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Phase", this.getDeathState());
		tag.putInt("Texture", this.getDeathState());
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEATH_STATE, 0);
		this.entityData.define(TEXTURE, 0);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			if (this.entityData.get(DEATH_STATE) == 0) {
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 0, false, false));
			} else {
				this.removeEffect(MobEffects.DAMAGE_RESISTANCE);
			}
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new RangedStrafeGladiatorAttackGoal(this, new FireballAttack(this)
				.setProjectileOriginOffset(0.8, 0.8, 0.8)
				.setDamage(DoomConfig.SERVER.gladiator_ranged_damage.get().floatValue()
						+ (this.entityData.get(DEATH_STATE) == 1 ? DoomConfig.SERVER.gladiator_phaseone_damage_boost.get().floatValue() : 0))
				.setSound(SoundEvents.FIRECHARGE_USE, 1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F)));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}

	public class FireballAttack extends AbstractDoubleRangedAttack {

		public FireballAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
				double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(DemonEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(DoomSounds.BALLISTA_FIRING.get(), 1, 1);
		}

		@Override
		public Projectile getProjectile(Level world, double d2, double d3, double d4) {
			return new CustomFireballEntity(world, this.parentEntity, d2, d3, d4, damage);

		}

		@Override
		public Projectile getProjectile2(Level world, double d2, double d3, double d4) {
			return new GladiatorMaceEntity(world, this.parentEntity, d2, d3, d4);
		}
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		boolean bl = target.hurt(DamageSource.mobAttack(this), (float) DoomConfig.SERVER.gladiator_melee_damage.get().floatValue()
				+ (this.entityData.get(DEATH_STATE) == 1 ? DoomConfig.SERVER.gladiator_phaseone_damage_boost.get().floatValue() : 0));
		if (bl) {
			target.setDeltaMovement(target.getDeltaMovement().multiply(1.4f, 1.4f, 1.4f));
			this.doEnchantDamageEffects(this, target);
			this.level.explode(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false,
					Level.ExplosionInteraction.BLOCK);
			target.invulnerableTime = 0;
		}
		return true;
	}

	public boolean tryAttack1(Entity target) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		boolean bl = target.hurt(DamageSource.mobAttack(this), (float) DoomConfig.SERVER.gladiator_melee_damage.get().floatValue()
				+ (this.entityData.get(DEATH_STATE) == 1 ? DoomConfig.SERVER.gladiator_phaseone_damage_boost.get().floatValue() : 0));
		if (bl) {
			target.setDeltaMovement(target.getDeltaMovement().multiply(1.4f, 1.4f, 1.4f));
			this.doEnchantDamageEffects(this, target);
			this.level.explode(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false,
					Level.ExplosionInteraction.BLOCK);
			target.invulnerableTime = 0;
		}
		return true;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, DoomConfig.SERVER.gladiator_health.get())
				.add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.gladiator_melee_damage.get())
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.9f).add(Attributes.ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.BARON_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.BARON_DEATH.get();
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
	public void baseTick() {
		super.baseTick();
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(64D, 64D, 64D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof GladiatorEntity && e.tickCount < 1) {
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
		return this.getAttckingState() == 1 || this.getAttckingState() == 4 ? false : super.hurt(source, amount);
	}

}
