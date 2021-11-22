package mod.azure.doom.entity.tiersuperheavy;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RangedStrafeGladiatorAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GladiatorEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final DataParameter<Integer> DEATH_STATE = EntityDataManager.defineId(GladiatorEntity.class,
			DataSerializers.INT);
	public static final DataParameter<Integer> TEXTURE = EntityDataManager.defineId(GladiatorEntity.class,
			DataSerializers.INT);
	private AnimationFactory factory = new AnimationFactory(this);

	public GladiatorEntity(EntityType<? extends DemonEntity> type, World worldIn) {
		super(type, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.entityData.get(DEATH_STATE) == 0 && event.isMoving()&& this.entityData.get(STATE) < 1) {
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
			this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::stop);
			this.setLastHurtMob(this.getLastHurtByMob());
			this.level.broadcastEntityEvent(this, (byte) 3);
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
			this.remove();
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
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DEATH_STATE, 0);
		this.entityData.define(TEXTURE, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new RangedStrafeGladiatorAttackGoal(this,
				new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.8, 0.8)
						.setDamage(DoomConfig.SERVER.gladiator_ranged_damage.get().floatValue()).setSound(
								SoundEvents.FIRECHARGE_USE, 1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F),
				1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
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

}
