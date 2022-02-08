package mod.azure.doom.entity.tiersuperheavy;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RangedStrafeGladiatorAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GladiatorEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(GladiatorEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> TEXTURE = SynchedEntityData.defineId(GladiatorEntity.class,
			EntityDataSerializers.INT);
	private final ServerBossEvent bossInfo = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
			BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_20)).setDarkenScreen(false)
					.setCreateWorldFog(false);
	private AnimationFactory factory = new AnimationFactory(this);

	public GladiatorEntity(EntityType<? extends DemonEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.entityData.get(DEATH_STATE) == 0 && event.isMoving() && this.entityData.get(STATE) < 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_phaseone", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 0 && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phaseone", false));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("shield_plant", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 1 && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_phasetwo", true));
			event.getController().setAnimationSpeed(1);
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 1 && (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phasetwo", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder()
				.addAnimation((this.entityData.get(DEATH_STATE) == 0 ? "idle_phaseone" : "idle_phasetwo"), true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 3
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone2", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 0 && this.entityData.get(STATE) == 4
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone2", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 1 && this.entityData.get(STATE) == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 1 && this.entityData.get(STATE) == 3
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo2", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(DEATH_STATE) == 1 && this.entityData.get(STATE) == 4
				&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo2", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GladiatorEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<GladiatorEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public int tickTimer() {
		return tickCount;
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
			if (this.entityData.get(DEATH_STATE) == 0) {
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
		this.goalSelector.addGoal(4, new RangedStrafeGladiatorAttackGoal(this,
				new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.8, 0.8)
						.setDamage(DoomConfig.SERVER.gladiator_ranged_damage.get().floatValue()).setSound(
								SoundEvents.FIRECHARGE_USE, 1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F),
				1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
	}
	
	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		boolean bl = target.hurt(DamageSource.mobAttack(this),
				DoomConfig.SERVER.gladiator_melee_damage.get().floatValue());
		if (bl) {
			target.setDeltaMovement(target.getDeltaMovement().multiply(1.4f, 1.4f, 1.4f));
			this.doEnchantDamageEffects(this, target);
			this.level.explode(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false,
					Explosion.BlockInteraction.BREAK);
			target.invulnerableTime = 0;
		}
		return bl;
	}

	public boolean doHurtTarget1(Entity target) {
		this.level.broadcastEntityEvent(this, (byte) 4);
		boolean bl = target.hurt(DamageSource.mobAttack(this),
				DoomConfig.SERVER.gladiator_melee_damage.get().floatValue());
		if (bl) {
			this.doEnchantDamageEffects(this, target);
			this.level.explode(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false,
					Explosion.BlockInteraction.BREAK);
			target.invulnerableTime = 0;
		}
		return bl;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.gladiator_health.get())
				.add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.gladiator_melee_damage.get())
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.BARON_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.BARON_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.BARON_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.BARON_STEP.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
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

}
