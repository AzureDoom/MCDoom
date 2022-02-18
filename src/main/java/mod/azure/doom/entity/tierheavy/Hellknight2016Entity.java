package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
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

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isAggressive() && this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (this.isAggressive() && animationSpeed > 0.35F && this.entityData.get(STATE) == 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (animationSpeed > 0.01F && this.hurtMarked) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
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
			if (this.level.isClientSide()) {
				this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.PINKY_STEP.get(),
						SoundSource.HOSTILE, 1.0F, 1.0F, true);
			}
		}
		if (event.sound.matches("talk")) {
			if (this.level.isClientSide()) {
				this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(),
						ModSoundEvents.HELLKNIGHT_AMBIENT.get(), SoundSource.HOSTILE, 1.0F, 1.0F, true);
			}
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public Hellknight2016Entity(EntityType<? extends Hellknight2016Entity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(3, new Hellknight2016Entity.AttackGoal(this, 1.5D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	public int getMaxFallDistance() {
		return 99;
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
	protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
		return 0;
	}

	public class AttackGoal extends Goal {
		private final DemonEntity entity;
		private final double speedModifier;
		private int attackTime;

		public AttackGoal(DemonEntity zombieIn, double speedIn) {
			this.entity = zombieIn;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
			this.speedModifier = speedIn;
		}

		public boolean canUse() {
			return this.entity.getTarget() != null;
		}

		public boolean canContinueToUse() {
			return this.canUse();
		}

		public void start() {
			super.start();
			this.entity.setAggressive(true);
		}

		public void stop() {
			super.stop();
			this.entity.setAggressive(false);
			this.entity.setAttackingState(0);
			this.attackTime = -1;
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
				this.attackTime++;
				this.entity.lookAt(livingentity, 30.0F, 30.0F);
				if (inLineOfSight && livingentity.distanceTo(entity) >= 3.0D) {
					this.entity.getNavigation().moveTo(livingentity, this.speedModifier);
					this.attackTime = -5;
				} else {
					if (this.attackTime == 1) {
						this.entity.setAttackingState(1);
					}
					if (this.attackTime == 4) {
						Vec3 vec3d = this.entity.getDeltaMovement();
						Vec3 vec3d2 = new Vec3(livingentity.getX() - this.entity.getX(), 0.0,
								livingentity.getZ() - this.entity.getZ());
						vec3d2 = vec3d2.normalize().scale(0.4).add(vec3d.scale(0.4));
						this.entity.setDeltaMovement(vec3d2.x, 0.5F, vec3d2.z);
					}
					if (this.attackTime == 9) {
						AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(),
								entity.getY(), entity.getZ());
						areaeffectcloudentity.setParticle(ParticleTypes.CRIMSON_SPORE);
						areaeffectcloudentity.setRadius(3.0F);
						areaeffectcloudentity.setDuration(5);
						areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
						entity.level.addFreshEntity(areaeffectcloudentity);
						this.entity.doHurtTarget(livingentity);
						livingentity.invulnerableTime = 0;
					}
					if (this.attackTime == 13) {
						this.attackTime = -5;
						this.entity.setAttackingState(0);
						this.entity.getNavigation().moveTo(livingentity, this.speedModifier);
					}
				}
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.hellknight2016_health.get())
				.add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.hellknight2016_melee_damage.get())
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 2.75F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.HELLKNIGHT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.HELLKNIGHT_DEATH.get();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

}