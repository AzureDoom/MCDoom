package mod.azure.doom.entity.tierboss;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.task.DemonMeleeAttack;
import mod.azure.doom.entity.task.DemonProjectileAttack;
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
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;

public class GladiatorEntity extends DemonEntity implements SmartBrainOwner<GladiatorEntity> {

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
			if (event.getAnimatable().getDeathState() == 0 && event.isMoving() && event.getAnimatable().getAttckingState() < 1) {
				event.getController().setAnimationSpeed(1.5);
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_phaseone"));
			}
			if (event.getAnimatable().getDeathState() == 0 && (dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phaseone"));
			if (event.getAnimatable().getDeathState() == 1 && (dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_phasetwo"));
			if (event.getAnimatable().getDeathState() == 0 && event.getAnimatable().getAttckingState() == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("shield_plant"));
			if (event.getAnimatable().getDeathState() == 1 && event.isMoving()) {
				event.getController().setAnimationSpeed(1.5);
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_phasetwo"));
			}
			if (event.getAnimatable().getDeathState() == 0 && event.getAnimatable().getAttckingState() == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone", LoopType.PLAY_ONCE));
			if (event.getAnimatable().getDeathState() == 0 && event.getAnimatable().getAttckingState() == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone2", LoopType.PLAY_ONCE));
			if (event.getAnimatable().getDeathState() == 0 && event.getAnimatable().getAttckingState() == 4 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phaseone3", LoopType.PLAY_ONCE));
			if (event.getAnimatable().getDeathState() == 1 && event.getAnimatable().getAttckingState() == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo", LoopType.PLAY_ONCE));
			if (event.getAnimatable().getDeathState() == 1 && event.getAnimatable().getAttckingState() == 3 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo2", LoopType.PLAY_ONCE));
			if (event.getAnimatable().getDeathState() == 1 && event.getAnimatable().getAttckingState() == 4 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("melee_phasetwo2", LoopType.PLAY_ONCE));
			return event.setAndContinue(RawAnimation.begin().thenLoop(event.getAnimatable().getDeathState() == 0 ? "idle_phaseone" : "idle_phasetwo"));
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
			if (this.getDeathState() == 0) {
				final var areaeffectcloudentity = new AreaEffectCloud(level, this.getX(), this.getY(), this.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				level.addFreshEntity(areaeffectcloudentity);
				goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
				setLastHurtMob(getLastHurtByMob());
				level.broadcastEntityEvent(this, (byte) 3);
			}
			if (this.getDeathState() == 1) 
				super.die(source);
		}
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 80 && this.getDeathState() == 0) {
			setHealth(getMaxHealth());
			setDeathState(1);
			deathTime = 0;
		}
		if (deathTime == 40 && this.getDeathState() == 1) {
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
			if (this.getDeathState() == 0) 
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000000, 0, false, false));
			else 
				removeEffect(MobEffects.DAMAGE_RESISTANCE);
		}
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<GladiatorEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<GladiatorEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<GladiatorEntity>());
	}

	@Override
	public BrainActivityGroup<GladiatorEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StrafeTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<GladiatorEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<GladiatorEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(0.75f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
	}

	@Override
	public BrainActivityGroup<GladiatorEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)), new SetWalkTargetToAttackTarget<>().speedMod(0.85F), new DemonProjectileAttack<>(30).attackInterval(mob -> 80).attackDamage(DoomConfig.cyberdemon_ranged_damage), new DemonMeleeAttack<>(20));
	}


	@Override
	protected void registerGoals() {
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		level.broadcastEntityEvent(this, (byte) 4);
		final var bl = target.hurt(damageSources().mobAttack(this), (float) DoomConfig.gladiator_melee_damage + (this.getDeathState() == 1 ? DoomConfig.gladiator_phaseone_damage_boost : 0));
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
		final var bl = target.hurt(damageSources().mobAttack(this), (float) DoomConfig.gladiator_melee_damage + (this.getDeathState() == 1 ? DoomConfig.gladiator_phaseone_damage_boost : 0));
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
		tickBrain(this);
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
