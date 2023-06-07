package mod.azure.doom.entity.tierambient;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

public class GoreNestEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public int spawnTimer = 0;

	public GoreNestEntity(EntityType<? extends GoreNestEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			event.getController().setAnimationSpeed(0.25);
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 60) {
			remove(RemovalReason.KILLED);
			dropExperience();
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, DoomConfig.SERVER.gorenest_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	protected void actuallyHurt(DamageSource source, float damageAmount) {
		if (source == damageSources().outOfWorld())
			remove(Entity.RemovalReason.KILLED);

		if (!(source.getEntity() instanceof Player))
			setHealth(5.0F);

		remove(Entity.RemovalReason.KILLED);
	}

	@Override
	public void aiStep() {
		if (level.isClientSide) {
			level.addParticle(DustParticleOptions.REDSTONE, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);
			level.addParticle(ParticleTypes.SOUL, getRandomX(0.2D), getRandomY(), getRandomZ(0.5D), 0.0D, 0D, 0D);
		}
		++spawnTimer;
		final var aabb = new AABB(blockPosition()).inflate(64D);
		final var entityCount = level.getEntities(EntityTypeTest.forClass(DemonEntity.class), aabb, Entity::isAlive).size();
		if (spawnTimer == 800 && entityCount <= 15 && !this.isNoAi())
			spawnWave();
		if (spawnTimer >= 810)
			spawnTimer = 0;
		super.aiStep();
	}

	public void spawnWave() {
		final var waveEntries = DoomConfig.SERVER.gorenest_wave_entries.get();
		final var random = this.getRandom().nextInt(-3, 3);

		for (var k = 1; k < 5; ++k) {
			final var waveentity = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(waveEntries.get(getRandom().nextInt(waveEntries.size())))).create(level);
			waveentity.setPos(this.getX() + random, this.getY() + 0.5D, this.getZ() + random);
			level.addFreshEntity(waveentity);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

}