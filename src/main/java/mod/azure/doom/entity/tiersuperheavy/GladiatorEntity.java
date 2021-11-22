package mod.azure.doom.entity.tiersuperheavy;

import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RangedStrafeGladiatorAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
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

	public static final TrackedData<Integer> DEATH_STATE = DataTracker.registerData(GladiatorEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> TEXTURE = DataTracker.registerData(GladiatorEntity.class,
			TrackedDataHandlerRegistry.INTEGER);

	private AnimationFactory factory = new AnimationFactory(this);

	public GladiatorEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.dataTracker.get(DEATH_STATE) == 0 && event.isMoving()&& this.dataTracker.get(STATE) < 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_phaseone", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && (this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phaseone", false));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("shield_plant", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_phasetwo", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && (this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phasetwo", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder()
				.addAnimation((this.dataTracker.get(DEATH_STATE) == 0 ? "idle_phaseone" : "idle_phasetwo"), true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 3
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone2", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 4
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone2", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && this.dataTracker.get(STATE) == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && this.dataTracker.get(STATE) == 3
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo2", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && this.dataTracker.get(STATE) == 4
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
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
		return age;
	}

	@Override
	public void onDeath(DamageSource source) {
		if (!this.world.isClient) {
			if (source == DamageSource.OUT_OF_WORLD) {
				this.setDeathState(1);
			}
			this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::stop);
			this.onAttacking(this.getAttacker());
			this.world.sendEntityStatus(this, (byte) 3);
		}
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

	public int getTextureState() {
		return this.dataTracker.get(TEXTURE);
	}

	public void setTextureState(int state) {
		this.dataTracker.set(TEXTURE, state);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DEATH_STATE, 0);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(4,
				new RangedStrafeGladiatorAttackGoal(this,
						new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(config.gladiator_ranged_damage).setSound(SoundEvents.ITEM_FIRECHARGE_USE,
										1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F),
						1.1D));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
	}

	public static boolean spawning(EntityType<GladiatorEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.gladiator_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.gladiator_melee_damage)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 50D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.BARON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.BARON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.BARON_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.BARON_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

}
