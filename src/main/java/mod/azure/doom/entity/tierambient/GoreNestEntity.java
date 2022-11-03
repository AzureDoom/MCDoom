package mod.azure.doom.entity.tierambient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomEntities;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GoreNestEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);

	public int spawnTimer = 0;

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GoreNestEntity>(this, "controller", 0, this::predicate));
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
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public GoreNestEntity(EntityType<? extends GoreNestEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 60) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.gorenest_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	protected void actuallyHurt(DamageSource damageSrc, float damageAmount) {
		if (!(damageSrc.getEntity() instanceof Player)) {
			this.setHealth(5.0F);
		} else {
			this.remove(RemovalReason.KILLED);
		}
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
		List<EntityType<?>> givenList = Arrays.asList(DoomEntities.HELLKNIGHT.get(),
				DoomEntities.POSSESSEDSCIENTIST.get(), DoomEntities.IMP.get(), DoomEntities.PINKY.get(),
				DoomEntities.CACODEMON.get(), DoomEntities.CHAINGUNNER.get(), DoomEntities.GARGOYLE.get(),
				DoomEntities.HELLKNIGHT2016.get(), DoomEntities.LOST_SOUL.get(),
				DoomEntities.POSSESSEDSOLDIER.get(), DoomEntities.SHOTGUNGUY.get(), DoomEntities.UNWILLING.get(),
				DoomEntities.ZOMBIEMAN.get(), DoomEntities.ARACHNOTRON.get(), DoomEntities.ARCHVILE.get(),
				DoomEntities.MECHAZOMBIE.get(), DoomEntities.PAIN.get(), DoomEntities.MANCUBUS.get());
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

	@Override
	public int tickTimer() {
		return tickCount;
	}

}