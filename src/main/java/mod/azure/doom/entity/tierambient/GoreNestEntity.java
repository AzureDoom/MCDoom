package mod.azure.doom.entity.tierambient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.ModEntityTypes;
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
import net.minecraftforge.fmllegacy.network.NetworkHooks;
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
		spawnTimer = (spawnTimer + 1) % 8;
		++this.tickCount;
		if (!level.isClientSide) {
			if (this.tickCount % 2400 == 0 && this.getSpawnTimer() <= 3) {
				this.spawnWave();
				spawnTimer = spawnTimer + 1;
			}
		}
		if ((this.tickCount % 2400) * 3 == 0) {
			this.remove(RemovalReason.KILLED);
		}
		super.aiStep();
	}

	public int getSpawnTimer() {
		return spawnTimer;
	}

	public void spawnWave() {
		Random rand = new Random();
		List<EntityType<?>> givenList = Arrays.asList(ModEntityTypes.HELLKNIGHT.get(),
				ModEntityTypes.POSSESSEDSCIENTIST.get(), ModEntityTypes.IMP.get(), ModEntityTypes.NIGHTMARE_IMP.get(),
				ModEntityTypes.PINKY.get(), ModEntityTypes.CACODEMON.get(), ModEntityTypes.CHAINGUNNER.get(),
				ModEntityTypes.GARGOYLE.get(), ModEntityTypes.HELLKNIGHT2016.get(), ModEntityTypes.IMP2016.get(),
				ModEntityTypes.LOST_SOUL.get(), ModEntityTypes.POSSESSEDSOLDIER.get(), ModEntityTypes.SHOTGUNGUY.get(),
				ModEntityTypes.UNWILLING.get(), ModEntityTypes.ZOMBIEMAN.get(), ModEntityTypes.ARACHNOTRON.get(),
				ModEntityTypes.ARCHVILE.get(), ModEntityTypes.MECHAZOMBIE.get(), ModEntityTypes.PAIN.get(),
				ModEntityTypes.MANCUBUS.get());

		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity = randomElement.create(level);
			fireballentity.setPos(this.getX() + 2.0D, this.getY() + 1.5D, this.getZ() + 2.0D);
			level.addFreshEntity(fireballentity);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity1 = randomElement.create(level);
			fireballentity1.setPos(this.getX() + -2.0D, this.getY() + 1.5D, this.getZ() + -2.0D);
			level.addFreshEntity(fireballentity1);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity11 = randomElement.create(level);
			fireballentity11.setPos(this.getX() + 1.0D, this.getY() + 1.5D, this.getZ() + 1.0D);
			level.addFreshEntity(fireballentity11);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity111 = randomElement.create(level);
			fireballentity111.setPos(this.getX() + -1.0D, this.getY() + 1.5D, this.getZ() + -1.0D);
			level.addFreshEntity(fireballentity111);
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