package mod.azure.doom.entity.tierambient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
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

	public GoreNestEntity(EntityType<? extends GoreNestEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			if (world.isClient) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("death", true));
				return PlayState.CONTINUE;
			}
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		event.getController().setAnimationSpeed(0.25);
		return PlayState.CONTINUE;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		super.takeKnockback(0, 0, 0);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void tickCramming() {
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GoreNestEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public static boolean spawning(EntityType<GoreNestEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 80) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (!(source.getSource() instanceof PlayerEntity)) {
			this.setHealth(5.0F);
		} else {
			this.remove(Entity.RemovalReason.KILLED);
		}
	}

	public void spawnWave() {
		Random rand = new Random();
		List<EntityType<?>> givenList = Arrays.asList(DoomEntities.HELLKNIGHT, DoomEntities.POSSESSEDSCIENTIST,
				DoomEntities.IMP, DoomEntities.PINKY, DoomEntities.CACODEMON, DoomEntities.CHAINGUNNER,
				DoomEntities.GARGOYLE, DoomEntities.HELLKNIGHT2016, DoomEntities.LOST_SOUL,
				DoomEntities.POSSESSEDSOLDIER, DoomEntities.SHOTGUNGUY, DoomEntities.UNWILLING,
				DoomEntities.ZOMBIEMAN, DoomEntities.ARACHNOTRON, DoomEntities.ARCHVILE,
				DoomEntities.MECHAZOMBIE, DoomEntities.PAIN, DoomEntities.MANCUBUS);

		SplittableRandom random = new SplittableRandom();
		int r = random.nextInt(-3, 3);
		for (int k = 1; k < 5; ++k) {
			for (int i = 0; i < 1; i++) {
				int randomIndex = rand.nextInt(givenList.size());
				EntityType<?> randomElement = givenList.get(randomIndex);
				Entity fireballentity = randomElement.create(world);
				fireballentity.refreshPositionAndAngles(this.getX() + r, this.getY() + 0.5D, this.getZ() + r, 0, 0);
				world.spawnEntity(fireballentity);
			}
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.gorenest_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		return entityData;
	}

	@Override
	public void tickMovement() {
		if (this.world.isClient) {
			this.world.addParticle(DustParticleEffect.DEFAULT, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D,
					this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
					(this.random.nextDouble() - 0.5D) * 2.0D);
			this.world.addParticle(ParticleTypes.SOUL, this.getParticleX(0.2D), this.getRandomBodyY(),
					this.getParticleZ(0.5D), 0.0D, 0D, 0D);
		}
		spawnTimer = (spawnTimer + 1) % 8;
		++this.age;
		if (this.age == 150) {
			this.spawnWave();
			this.remove(RemovalReason.KILLED);
		}
		super.tickMovement();
	}

	public int getSpawnTimer() {
		return spawnTimer;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}
}