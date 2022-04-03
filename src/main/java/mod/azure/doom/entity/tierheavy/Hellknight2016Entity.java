package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.ModSoundEvents;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
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
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("jumpattack", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<Hellknight2016Entity> controller = new AnimationController<Hellknight2016Entity>(this,
				"controller", 0, this::predicate);
		AnimationController<Hellknight2016Entity> controller1 = new AnimationController<Hellknight2016Entity>(this,
				"controller1", 0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.PINKY_STEP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
		if (event.sound.matches("attack")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
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
		private final Hellknight2016Entity entity;
		private final double speedModifier;
		private int attackTime;

		public AttackGoal(Hellknight2016Entity zombieIn, double speedIn) {
			this.entity = zombieIn;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.JUMP));
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
				if (inLineOfSight) {
					if (this.entity.distanceTo(livingentity) >= 3.0D) {
						this.entity.getNavigation().startMovingTo(livingentity, this.speedModifier);
						this.attackTime = -5;
					} else {
						if (this.attackTime == 1) {
							this.entity.setAttackingState(1);
						}
						if (this.attackTime == 4) {
							Vec3d vec3d = this.entity.getVelocity();
							Vec3d vec3d2 = new Vec3d(livingentity.getX() - this.entity.getX(), 0.0,
									livingentity.getZ() - this.entity.getZ());
							vec3d2 = vec3d2.normalize().multiply(0.4).add(vec3d.multiply(0.4));
							this.entity.setVelocity(vec3d2.x, 0.5F, vec3d2.z);
						}
						if (this.attackTime == 9) {
							AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world,
									entity.getX(), entity.getY(), entity.getZ());
							areaeffectcloudentity.setParticleType(ParticleTypes.CRIMSON_SPORE);
							areaeffectcloudentity.setRadius(3.0F);
							areaeffectcloudentity.setDuration(5);
							areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
							entity.world.spawnEntity(areaeffectcloudentity);
							this.entity.tryAttack(livingentity);
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
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.HELLKNIGHT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.HELLKNIGHT_DEATH;
	}

}