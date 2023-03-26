package mod.azure.doom.entity.tierambient;

import java.util.Arrays;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomEntities;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
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
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, DoomConfig.gorenest_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
		final var i = level.getEntities(EntityTypeTest.forClass(DemonEntity.class), aabb, Entity::isAlive).size();
		if (spawnTimer == 800 && i <= 15)
			spawnWave();
		if (spawnTimer >= 810)
			spawnTimer = 0;
		super.aiStep();
	}

	public void spawnWave() {
		final var givenList = Arrays.asList(DoomEntities.HELLKNIGHT, DoomEntities.POSSESSEDSCIENTIST, DoomEntities.IMP, DoomEntities.PINKY, DoomEntities.CACODEMON, DoomEntities.CHAINGUNNER, DoomEntities.GARGOYLE, DoomEntities.HELLKNIGHT2016, DoomEntities.LOST_SOUL, DoomEntities.POSSESSEDSOLDIER, DoomEntities.SHOTGUNGUY, DoomEntities.UNWILLING, DoomEntities.ZOMBIEMAN, DoomEntities.ARACHNOTRON, DoomEntities.ARCHVILE, DoomEntities.MECHAZOMBIE, DoomEntities.PAIN, DoomEntities.MANCUBUS);
		final var r = random.nextInt(-3, 3);

		for (var k = 1; k < 5; ++k) {
			for (int i = 0; i < 1; i++) {
				final var waveentity = givenList.get(random.nextInt(givenList.size())).create(level);
				waveentity.setPos(this.getX() + r, this.getY() + 0.5D, this.getZ() + r);
				level.addFreshEntity(waveentity);
			}
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