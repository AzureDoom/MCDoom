package mod.azure.doom.entity.tierambient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
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
		return PlayState.CONTINUE;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	public void takeKnockback(float f, double d, double e) {
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
			this.remove();
		}
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (!(source.getSource() instanceof PlayerEntity)) {
			this.setHealth(5.0F);
		} else {
			this.remove();
		}
	}

	public void spawnWave() {
		Random rand = new Random();
		List<EntityType<?>> givenList = Arrays.asList(ModEntityTypes.HELLKNIGHT, ModEntityTypes.POSSESSEDSCIENTIST,
				ModEntityTypes.IMP, ModEntityTypes.NIGHTMARE_IMP, ModEntityTypes.PINKY, ModEntityTypes.CACODEMON,
				ModEntityTypes.CHAINGUNNER, ModEntityTypes.GARGOYLE, ModEntityTypes.HELLKNIGHT2016,
				ModEntityTypes.IMP2016, ModEntityTypes.LOST_SOUL, ModEntityTypes.POSSESSEDSOLDIER,
				ModEntityTypes.SHOTGUNGUY, ModEntityTypes.UNWILLING, ModEntityTypes.ZOMBIEMAN,
				ModEntityTypes.ARACHNOTRON, ModEntityTypes.ARCHVILE, ModEntityTypes.MECHAZOMBIE, ModEntityTypes.PAIN,
				ModEntityTypes.MANCUBUS);

		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity = randomElement.create(world);
			fireballentity.refreshPositionAndAngles(this.getX() + 2.0D, this.getY() + 0.5D, this.getZ() + 2.0D, 0, 0);
			world.spawnEntity(fireballentity);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity1 = randomElement.create(world);
			fireballentity1.refreshPositionAndAngles(this.getX() + -2.0D, this.getY() + 0.5D, this.getZ() + -2.0D, 0,
					0);
			world.spawnEntity(fireballentity1);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity11 = randomElement.create(world);
			fireballentity11.refreshPositionAndAngles(this.getX() + 1.0D, this.getY() + 0.5D, this.getZ() + 1.0D, 0, 0);
			world.spawnEntity(fireballentity11);
		}
		for (int i = 0; i < 1; i++) {
			int randomIndex = rand.nextInt(givenList.size());
			EntityType<?> randomElement = givenList.get(randomIndex);
			Entity fireballentity111 = randomElement.create(world);
			fireballentity111.refreshPositionAndAngles(this.getX() + -1.0D, this.getY() + 0.5D, this.getZ() + -1.0D, 0,
					0);
			world.spawnEntity(fireballentity111);
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.gorenest_health)
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
			this.world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getParticleX(0.5D),
					this.getRandomBodyY() - 0.25D, this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D,
					-this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
			this.world.addParticle(ParticleTypes.SOUL, this.getParticleX(0.2D), this.getRandomBodyY(),
					this.getParticleZ(0.5D), 0.0D, 0D, 0D);
		}
		spawnTimer = (spawnTimer + 1) % 8;
		++this.age;
		if (!world.isClient) {
			if (this.age % 2400 == 0 && this.getSpawnTimer() <= 3) {
				this.spawnWave();
			}
		}
		if ((this.age % 2400) * 3 == 0) {
			this.remove();
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