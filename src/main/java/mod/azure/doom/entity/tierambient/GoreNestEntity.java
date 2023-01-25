package mod.azure.doom.entity.tierambient;

import java.util.Arrays;
import java.util.List;

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
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;

public class GoreNestEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public int spawnTimer = 0;

	public GoreNestEntity(EntityType<? extends GoreNestEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			event.getController().setAnimationSpeed(0.25);
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
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
		++this.deathTime;
		if (this.deathTime == 60) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.gorenest_health).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	protected void actuallyHurt(DamageSource source, float damageAmount) {
		if (source == DamageSource.OUT_OF_WORLD)
			this.remove(Entity.RemovalReason.KILLED);

		if (!(source.getEntity() instanceof Player))
			this.setHealth(5.0F);

		this.remove(Entity.RemovalReason.KILLED);
	}

	@Override
	public void aiStep() {
		if (this.level.isClientSide) {
			this.level.addParticle(DustParticleOptions.REDSTONE, this.getRandomX(0.5D), this.getRandomY(),
					this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
					(this.random.nextDouble() - 0.5D) * 2.0D);
			this.level.addParticle(ParticleTypes.SOUL, this.getRandomX(0.2D), this.getRandomY(), this.getRandomZ(0.5D),
					0.0D, 0D, 0D);
		}
		++this.spawnTimer;
		final AABB aabb = new AABB(this.blockPosition()).inflate(64D);
		int i = this.level.getEntities(EntityTypeTest.forClass(DemonEntity.class), aabb, Entity::isAlive).size();
		if (this.spawnTimer == 800 && i <= 15) {
			this.spawnWave();
		}
		if (this.spawnTimer >= 810)
			this.spawnTimer = 0;
		super.aiStep();
	}

	public void spawnWave() {
		List<EntityType<?>> givenList = Arrays.asList(DoomEntities.HELLKNIGHT, DoomEntities.POSSESSEDSCIENTIST,
				DoomEntities.IMP, DoomEntities.PINKY, DoomEntities.CACODEMON, DoomEntities.CHAINGUNNER,
				DoomEntities.GARGOYLE, DoomEntities.HELLKNIGHT2016, DoomEntities.LOST_SOUL,
				DoomEntities.POSSESSEDSOLDIER, DoomEntities.SHOTGUNGUY, DoomEntities.UNWILLING, DoomEntities.ZOMBIEMAN,
				DoomEntities.ARACHNOTRON, DoomEntities.ARCHVILE, DoomEntities.MECHAZOMBIE, DoomEntities.PAIN,
				DoomEntities.MANCUBUS);
		int r = this.random.nextInt(-3, 3);

		for (int k = 1; k < 5; ++k) {
			for (int i = 0; i < 1; i++) {
				int randomIndex = this.random.nextInt(givenList.size());
				EntityType<?> randomElement = givenList.get(randomIndex);
				Entity waveentity = randomElement.create(level);
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