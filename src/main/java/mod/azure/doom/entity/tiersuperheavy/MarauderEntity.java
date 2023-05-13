package mod.azure.doom.entity.tiersuperheavy;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.DoomAnimationsDefault;
import mod.azure.doom.entity.task.DemonMeleeAttack;
import mod.azure.doom.entity.task.DemonProjectileAttack;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

public class MarauderEntity extends DemonEntity implements SmartBrainOwner<MarauderEntity> {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private int targetChangeTime;

	public MarauderEntity(EntityType<MarauderEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && event.getAnimatable().getAttckingState() == 0)
				return event.setAndContinue(DoomAnimationsDefault.WALKING);
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(DoomAnimationsDefault.DEATH);
			return event.setAndContinue(DoomAnimationsDefault.IDLE);
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (event.getAnimatable().getAttckingState() == 2 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(DoomAnimationsDefault.ATTACKING);
			if (event.getAnimatable().getAttckingState() == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(DoomAnimationsDefault.RANGED);
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (level.isClientSide())
					getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.SUPER_SHOTGUN_SHOOT, SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<MarauderEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<MarauderEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<MarauderEntity>());
	}

	@Override
	public BrainActivityGroup<MarauderEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StrafeTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<MarauderEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<MarauderEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
	}

	@Override
	public BrainActivityGroup<MarauderEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)), new SetWalkTargetToAttackTarget<>().speedMod(1.05F), new DemonProjectileAttack<>(7).attackInterval(mob -> 40).attackDamage(DoomMod.config.baron_ranged_damage), new DemonMeleeAttack<>(5));
	}

	@Override
	protected void registerGoals() {
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomMod.config.marauder_health).add(Attributes.ATTACK_DAMAGE, DoomMod.config.marauder_axe_damage).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	public boolean isLookingAtMe(Player player) {
		final Vec3 vector3d = player.getViewVector(1.0F).normalize();
		Vec3 vector3d1 = new Vec3(this.getX() - player.getX(), getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
		final double d0 = vector3d1.length();
		vector3d1 = vector3d1.normalize();
		final double d1 = vector3d.dot(vector3d1);
		return d1 > 1.0D - 0.025D / d0 ? player.hasLineOfSight(this) : false;
	}

	@Override
	public void aiStep() {
		if (level.isClientSide) {
			for (int i = 0; i < 2; ++i) {
				level.addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
			}
		}

		jumping = false;
		if (!level.isClientSide) {
			updatePersistentAnger((ServerLevel) level, true);
		}

		super.aiStep();
	}

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
		if (level.isDay() && tickCount >= targetChangeTime + 600) {
			final var f = getLightLevelDependentMagicValue();
			if (f > 0.5F && level.canSeeSky(blockPosition()) && random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F)
				setTarget((LivingEntity) null);
		}

		super.customServerAiStep();
	}

	public boolean teleport() {
		if (!level.isClientSide() && isAlive()) {
			final double d0 = this.getX() + (random.nextDouble() - 0.5D) * 12.0D;
			final double d1 = this.getY() + (random.nextInt(64) - 32);
			final double d2 = this.getZ() + (random.nextDouble() - 0.5D) * 12.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleport(double x, double y, double z) {
		final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

		while (blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		final BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
		final boolean flag = blockstate.getMaterial().blocksMotion();
		final boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			final boolean flag2 = randomTeleport(x, y, z, true);

			return flag2;
		} else {
			return false;
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.ZOMBIEMAN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ZOMBIEMAN_DEATH;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}
}