package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;

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

public class Hellknight2016Entity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public Hellknight2016Entity(EntityType<? extends Hellknight2016Entity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && !isAggressive() && onGround)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (isAggressive() && this.walkAnimation.speed() > 0.35F && onGround)
				return event.setAndContinue(RawAnimation.begin().thenLoop("run"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("jumpattack", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		goalSelector.addGoal(3, new Hellknight2016Entity.AttackGoal(this, 1.5D));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	@Override
	public int getMaxFallDistance() {
		return 99;
	}

	@Override
	protected void updateControlFlags() {
		final boolean flag = getTarget() != null && hasLineOfSight(getTarget());
		goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 30) {
			remove(RemovalReason.KILLED);
			dropExperience();
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
			entity = zombieIn;
			setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
			speedModifier = speedIn;
		}

		@Override
		public boolean canUse() {
			return entity.getTarget() != null;
		}

		@Override
		public boolean canContinueToUse() {
			return canUse();
		}

		@Override
		public void start() {
			super.start();
			entity.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			entity.setAggressive(false);
			entity.setAttackingState(0);
			attackTime = -1;
		}

		@Override
		public void tick() {
			final LivingEntity livingentity = entity.getTarget();
			if (livingentity != null) {
				final boolean inLineOfSight = entity.getSensing().hasLineOfSight(livingentity);
				attackTime++;
				entity.lookAt(livingentity, 30.0F, 30.0F);
				final AABB aabb = new AABB(entity.blockPosition()).inflate(5D);
				final AABB aabb2 = new AABB(entity.blockPosition()).inflate(3D);
				if (inLineOfSight) {
					entity.getNavigation().moveTo(livingentity, speedModifier);
					if (entity.getCommandSenderWorld().getEntities(entity, aabb).contains(livingentity)) {
						if (attackTime == 1) {
							entity.setAttackingState(1);
						}
						if (attackTime == 4) {
							final Vec3 vec3d = entity.getDeltaMovement();
							Vec3 vec3d2 = new Vec3(livingentity.getX() - entity.getX(), 0.0, livingentity.getZ() - entity.getZ());
							vec3d2 = vec3d2.normalize().scale(0.4).add(vec3d.scale(0.4));
							entity.setDeltaMovement(vec3d2.x, 0.5F, vec3d2.z);
							entity.lookAt(livingentity, 30.0F, 30.0F);
						}
						if (attackTime == 9) {
							final AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(), entity.getY(), entity.getZ());
							areaeffectcloudentity.setParticle(ParticleTypes.CRIMSON_SPORE);
							areaeffectcloudentity.setRadius(3.0F);
							areaeffectcloudentity.setDuration(5);
							areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
							entity.getCommandSenderWorld().getEntities(entity, aabb2).forEach(e -> {
								if (e instanceof LivingEntity) {
									e.hurt(damageSources().mobAttack(entity), (float) DoomConfig.hellknight2016_melee_damage);
									entity.level.addFreshEntity(areaeffectcloudentity);
								}
							});
							livingentity.invulnerableTime = 0;
						}
						if (attackTime >= 13) {
							attackTime = -5;
							entity.setAttackingState(0);
							entity.getNavigation().moveTo(livingentity, speedModifier);
						}
					}
				} else {
					--attackTime;
				}
			}
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.hellknight2016_health).add(Attributes.ATTACK_DAMAGE, DoomConfig.hellknight2016_melee_damage).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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