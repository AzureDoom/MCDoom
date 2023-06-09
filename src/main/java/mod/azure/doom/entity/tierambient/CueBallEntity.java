package mod.azure.doom.entity.tierambient;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.DoomAnimationsDefault;
import mod.azure.doom.entity.task.DemonMeleeAttack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
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

public class CueBallEntity extends DemonEntity implements SmartBrainOwner<CueBallEntity> {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CueBallEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FUSE_SPEED = SynchedEntityData.defineId(CueBallEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(CueBallEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(CueBallEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public int flameTimer;
	private int lastFuseTime;
	private int currentFuseTime;
	private int fuseTime = 30;
	private int explosionRadius = 4;

	public CueBallEntity(EntityType<CueBallEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (event.isMoving())
				return event.setAndContinue(DoomAnimationsDefault.WALK);
			if (dead || getHealth() < 0.01 || isDeadOrDying() && event.getAnimatable().getVariant() == 3)
				return event.setAndContinue(DoomAnimationsDefault.DEATH);
			return event.setAndContinue(DoomAnimationsDefault.IDLE);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, DoomMod.config.cueball_health).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(3, x, z);
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (getVariant() != 3) {
			if (deathTime == 30) {
				remove(RemovalReason.KILLED);
				dropExperience();
				if (!level().isClientSide)
					explode();
			}
		} else {
			if (deathTime == 5) {
				remove(RemovalReason.KILLED);
				dropExperience();
				if (!level().isClientSide) {
					final var aabb = new AABB(blockPosition().above()).inflate(24D, 24D, 24D);
					level().getEntities(this, aabb).forEach(e -> {
						if (e.isAlive() && e instanceof DemonEntity)
							((LivingEntity) e).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
					});
				}
			}
		}
	}

	protected void explode() {
		level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, Level.ExplosionInteraction.NONE);
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
	public List<ExtendedSensor<CueBallEntity>> getSensors() {
		return ObjectArrayList.of(new NearbyLivingEntitySensor<CueBallEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<CueBallEntity>());
	}

	@Override
	public BrainActivityGroup<CueBallEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new MoveToWalkTarget<>());
	}

	@Override
	public BrainActivityGroup<CueBallEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<CueBallEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player && ((Player) target).isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(0.75f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
	}

	@Override
	public BrainActivityGroup<CueBallEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()), new SetWalkTargetToAttackTarget<>().speedMod(0.85F), new DemonMeleeAttack<>(0));
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
	protected void registerGoals() {
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 2;
		if (level().isClientSide)
			if (getVariant() == 3)
				for (var i = 0; i < 2; ++i)
					level().addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY() - 0.25D, getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);

		if (isAlive() && getVariant() != 3) {
			int i;
			lastFuseTime = currentFuseTime;
			if (isIgnited())
				setFuseSpeed(1);
			if ((i = getFuseSpeed()) > 0 && currentFuseTime == 0)
				this.gameEvent(GameEvent.PRIME_FUSE);
			currentFuseTime += i;
			if (currentFuseTime < 0)
				currentFuseTime = 0;
			if (currentFuseTime >= fuseTime) {
				currentFuseTime = fuseTime;
				if (!(getHealth() < 0.01 || isDeadOrDying()))
					explode();
				kill();
			}
		}
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(VARIANT, 0);
		entityData.define(FUSE_SPEED, -1);
		entityData.define(CHARGED, false);
		entityData.define(IGNITED, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setVariant(tag.getInt("Variant"));
		if (entityData.get(CHARGED).booleanValue())
			tag.putBoolean("powered", true);
		tag.putShort("Fuse", (short) fuseTime);
		tag.putByte("ExplosionRadius", (byte) explosionRadius);
		tag.putBoolean("ignited", isIgnited());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());
		entityData.set(CHARGED, tag.getBoolean("powered"));
		if (tag.contains("Fuse", 99))
			fuseTime = tag.getShort("Fuse");
		if (tag.contains("ExplosionRadius", 99))
			explosionRadius = tag.getByte("ExplosionRadius");
		if (tag.getBoolean("ignited"))
			ignite();
	}

	public int getVariant() {
		return Mth.clamp(entityData.get(VARIANT), 1, 3);
	}

	public void setVariant(int variant) {
		entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 3;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		final var var = getRandom().nextInt(0, 4);
		setVariant(var);
		return spawnDataIn;
	}

	@Override
	public Component getCustomName() {
		return getVariant() == 3 ? Component.translatable("entity.doom.screecher") : getVariant() == 2 ? Component.translatable("entity.doom.possessedengineer") : super.getCustomName();
	}

	public float getClientFuseTime(float timeDelta) {
		return Mth.lerp(timeDelta, lastFuseTime, currentFuseTime) / (fuseTime - 2);
	}

	public int getFuseSpeed() {
		return entityData.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		entityData.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean isIgnited() {
		return entityData.get(IGNITED);
	}

	public void ignite() {
		entityData.set(IGNITED, true);
	}

}
