package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Hellknight2016Entity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public Hellknight2016Entity(EntityType<? extends Hellknight2016Entity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && !this.isAggressive() && this.onGround)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (this.isAggressive() && animationSpeedOld > 0.35F && this.onGround)
				return event.setAndContinue(RawAnimation.begin().thenLoop("run"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (this.level.isClientSide())
					this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
							SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("jumpattack", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (this.level.isClientSide())
					this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(),
							SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
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
		private final Hellknight2016Entity entity;
		private final double speedModifier;
		private int attackTime;

		public AttackGoal(Hellknight2016Entity zombieIn, double speedIn) {
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
				final AABB aabb = new AABB(this.entity.blockPosition()).inflate(5D);
				final AABB aabb2 = new AABB(this.entity.blockPosition()).inflate(3D);
				if (inLineOfSight) {
					this.entity.getNavigation().moveTo(livingentity, this.speedModifier);
					if (this.entity.getCommandSenderWorld().getEntities(this.entity, aabb).contains(livingentity)) {
						if (this.attackTime == 1) {
							this.entity.setAttackingState(1);
						}
						if (this.attackTime == 4) {
							Vec3 vec3d = this.entity.getDeltaMovement();
							Vec3 vec3d2 = new Vec3(livingentity.getX() - this.entity.getX(), 0.0,
									livingentity.getZ() - this.entity.getZ());
							vec3d2 = vec3d2.normalize().scale(0.4).add(vec3d.scale(0.4));
							this.entity.setDeltaMovement(vec3d2.x, 0.5F, vec3d2.z);
							this.entity.lookAt(livingentity, 30.0F, 30.0F);
						}
						if (this.attackTime == 9) {
							AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(),
									entity.getY(), entity.getZ());
							areaeffectcloudentity.setParticle(ParticleTypes.CRIMSON_SPORE);
							areaeffectcloudentity.setRadius(3.0F);
							areaeffectcloudentity.setDuration(5);
							areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
							this.entity.getCommandSenderWorld().getEntities(this.entity, aabb2).forEach(e -> {
								if ((e instanceof LivingEntity)) {
									e.hurt(DamageSource.mobAttack(this.entity),
											((float) DoomConfig.hellknight2016_melee_damage));
									entity.level.addFreshEntity(areaeffectcloudentity);
								}
							});
							livingentity.invulnerableTime = 0;
						}
						if (this.attackTime >= 13) {
							this.attackTime = -5;
							this.entity.setAttackingState(0);
							this.entity.getNavigation().moveTo(livingentity, this.speedModifier);
						}
					}
				} else {
					--this.attackTime;
				}
			}
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.hellknight2016_health)
				.add(Attributes.ATTACK_DAMAGE, DoomConfig.hellknight2016_melee_damage)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
		return DoomSounds.HELLKNIGHT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.HELLKNIGHT_DEATH;
	}

}