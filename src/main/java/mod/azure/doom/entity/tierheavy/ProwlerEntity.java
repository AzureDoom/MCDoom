package mod.azure.doom.entity.tierheavy;

import java.util.List;
import java.util.SplittableRandom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
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

public class ProwlerEntity extends DemonEntity implements SmartBrainOwner<ProwlerEntity> {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ProwlerEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private int targetChangeTime;

	public ProwlerEntity(EntityType<? extends ProwlerEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving() && hurtTime == 0)
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (event.getAnimatable().getAttckingState() == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attack", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 50) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	@Override
	protected Brain.Provider<?> brainProvider() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<ProwlerEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<ProwlerEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<ProwlerEntity>());
	}

	@Override
	public BrainActivityGroup<ProwlerEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StrafeTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<ProwlerEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<ProwlerEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
	}

	@Override
	public BrainActivityGroup<ProwlerEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)), new SetWalkTargetToAttackTarget<>().speedMod(1.05F), new DemonProjectileAttack<>(7).attackDamage(DoomMod.config.prowler_ranged_damage).attackInterval(mob -> 80), new AnimatableMeleeAttack<>(20));
	}

	@Override
	public void aiStep() {
		if (level.isClientSide) {
			if (getVariant() == 1)
				for (var i = 0; i < 2; ++i)
					level.addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
			else
				for (var i = 0; i < 2; ++i)
					level.addParticle(ParticleTypes.COMPOSTER, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
		}
		jumping = false;
		if (!level.isClientSide)
			updatePersistentAnger((ServerLevel) level, true);

		super.aiStep();
	}

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
		if (level.isDay() && tickCount >= targetChangeTime + 600) {
			final var f = getLightLevelDependentMagicValue();
			if (f > 0.5F && level.canSeeSky(blockPosition()) && random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				setTarget((LivingEntity) null);
				this.teleport();
			}
		}
		super.customServerAiStep();
	}

	public boolean teleport() {
		if (!level.isClientSide() && isAlive())
			return this.teleport(this.getX() + (random.nextDouble() - 0.5D) * 16.0D, this.getY() + (random.nextInt(64) - 16), this.getZ() + (random.nextDouble() - 0.5D) * 16.0D);
		else
			return false;
	}

	private boolean teleport(double x, double y, double z) {
		final var blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);

		while (blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion())
			blockpos$mutableblockpos.move(Direction.DOWN);

		final var blockstate = level.getBlockState(blockpos$mutableblockpos);
		final var flag = blockstate.getMaterial().blocksMotion();
		final var flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1)
			return randomTeleport(x, y, z, true);
		else
			return false;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, DoomMod.config.prowler_health).add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_DAMAGE, DoomMod.config.prowler_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setVariant(tag.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());
	}

	public int getVariant() {
		return Mth.clamp(entityData.get(VARIANT), 1, 2);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final SplittableRandom random = new SplittableRandom();
		final int var = random.nextInt(0, 3);
		setVariant(var);
		return spawnDataIn;
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (getVariant() == 2 && target instanceof LivingEntity)
			((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), this);
		return super.doHurtTarget(target);
	}

}