package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
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

public class Hellknight2016Entity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public Hellknight2016Entity(EntityType<Hellknight2016Entity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isAttacking() && this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (this.isAttacking() && lastLimbDistance > 0.35F && this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (lastLimbDistance > 0.01F && this.velocityModified) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (!event.isMoving() && this.dataTracker.get(STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("jumpattack", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<Hellknight2016Entity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<Hellknight2016Entity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	public static boolean spawning(EntityType<Hellknight2016Entity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(3, new Hellknight2016Entity.AttackGoal(this, 1.5D));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	@Override
	public int getSafeFallDistance() {
		return 99;
	}

	@Override
	protected int computeFallDamage(float fallDistance, float damageMultiplier) {
		return 0;
	}

	public class AttackGoal extends Goal {
		private final DemonEntity entity;
		private final double speedModifier;
		private int attackTime;

		public AttackGoal(DemonEntity zombieIn, double speedIn) {
			this.entity = zombieIn;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
			this.speedModifier = speedIn;
		}

		public boolean canStart() {
			return this.entity.getTarget() != null;
		}

		public boolean shouldContinue() {
			return this.canStart();
		}

		public void start() {
			super.start();
			this.entity.setAttacking(true);
		}

		public void stop() {
			super.stop();
			this.entity.setAttacking(false);
			this.entity.setAttackingState(0);
			this.attackTime = -1;
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
				this.attackTime++;
				this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
				double d0 = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(),
						livingentity.getZ());
				double d1 = this.getAttackReachSqr(livingentity);
				if (inLineOfSight && livingentity.distanceTo(entity) >= 4.0D && this.attackTime < 0) {
					this.entity.getNavigation().startMovingTo(livingentity, this.speedModifier);
					this.attackTime = -5;
				} else {
					this.entity.getNavigation().stop();
					if (this.attackTime == 1) {
						if (d0 <= d1) {
							this.entity.setAttackingState(1);
						}
					}
					if (this.attackTime == 10) {
						AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world,
								entity.getX(), entity.getY(), entity.getZ());
						areaeffectcloudentity.setParticleType(ParticleTypes.CRIMSON_SPORE);
						areaeffectcloudentity.setRadius(3.0F);
						areaeffectcloudentity.setDuration(5);
						areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
						entity.world.spawnEntity(areaeffectcloudentity);
						this.entity.getJumpControl().setActive();
						if (d0 <= d1) {
							this.entity.tryAttack(livingentity);
						}
						livingentity.timeUntilRegen = 0;
					}
					if (this.attackTime == 13) {
						this.attackTime = -5;
						this.entity.setAttackingState(0);
						this.entity.getNavigation().startMovingTo(livingentity, this.speedModifier);
					}
				}
			}
		}

		protected double getAttackReachSqr(LivingEntity entity) {
			return (double) (this.entity.getWidth() * 2.5F * this.entity.getWidth() * 2.5F + entity.getWidth());
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.hellknight2016_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.hellknight2016_melee_damage)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 50D)
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
		return 2.75F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.HELLKNIGHT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.HELLKNIGHT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.HELLKNIGHT_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

}