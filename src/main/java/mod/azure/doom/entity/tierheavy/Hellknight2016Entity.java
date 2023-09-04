package mod.azure.doom.entity.tierheavy;

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
import mod.azure.doom.entity.DoomAnimationsDefault;
import mod.azure.doom.entity.task.DemonMeleeAttack;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
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

public class Hellknight2016Entity extends DemonEntity implements SmartBrainOwner<Hellknight2016Entity> {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public Hellknight2016Entity(EntityType<? extends Hellknight2016Entity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && !isAggressive() && this.onGround())
				return event.setAndContinue(DoomAnimationsDefault.WALKING);
			if (isAggressive() && this.walkAnimation.speed() > 0.35F && this.onGround() && !this.swinging)
				return event.setAndContinue(DoomAnimationsDefault.RUN);
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(DoomAnimationsDefault.DEATH);
			return event.setAndContinue(DoomAnimationsDefault.IDLE);
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (level().isClientSide())
					level().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP.get(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
			if (event.getKeyframeData().getSound().matches("attack"))
				if (level().isClientSide())
					level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}).triggerableAnim("attack", RawAnimation.begin().then("jumpattack", LoopType.PLAY_ONCE)));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
		super.customServerAiStep();
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<Hellknight2016Entity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<Hellknight2016Entity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<Hellknight2016Entity>());
	}

	@Override
	public BrainActivityGroup<Hellknight2016Entity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<Hellknight2016Entity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<Hellknight2016Entity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(0.75f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
	}

	@Override
	public BrainActivityGroup<Hellknight2016Entity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()), new SetWalkTargetToAttackTarget<>().speedMod(1.25F), new DemonMeleeAttack<>(5));
	}

	@Override
	public double getMeleeAttackRangeSqr(LivingEntity livingEntity) {
		return this.getBbWidth() * 1.5f * (this.getBbWidth() * 1.5f + livingEntity.getBbWidth());
	}

	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		var distance = this.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
		return distance <= this.getMeleeAttackRangeSqr(livingEntity);
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

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomConfig.SERVER.hellknight2016_health.get()).add(Attributes.ATTACK_DAMAGE, DoomConfig.SERVER.hellknight2016_melee_damage.get()).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
		return DoomSounds.HELLKNIGHT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.HELLKNIGHT_DEATH.get();
	}

}