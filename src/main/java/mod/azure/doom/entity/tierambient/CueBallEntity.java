package mod.azure.doom.entity.tierambient;

import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.IgniteDemonGoal;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CueBallEntity extends DemonEntity implements GeoEntity {

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(CueBallEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(CueBallEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(CueBallEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> IGNITED = DataTracker.registerData(CueBallEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public int flameTimer;
	private int lastFuseTime;
	private int currentFuseTime;
	private int fuseTime = 30;
	private int explosionRadius = 4;

	public CueBallEntity(EntityType<? extends DemonEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
			if (this.dead || this.getHealth() < 0.01 || this.isDead())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
			if (!this.world.isClient && this.getVariant() == 1) {
				this.explode();
			}
			if (!this.world.isClient && this.getVariant() == 3) {
				final Box aabb = new Box(this.getBlockPos().up()).expand(24D, 24D, 24D);
				this.getWorld().getOtherEntities(this, aabb).forEach(e -> {
					if (e.isAlive() && e instanceof DemonEntity) {
						((LivingEntity) e).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000, 1));
					}
				});
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 2;
		if (this.world.isClient()) {
			if (this.getVariant() == 3) {
				for (int i = 0; i < 2; ++i) {
					this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D,
							this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D,
							-this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
				}
			}
		}
		if (this.isAlive() && this.getVariant() != 3) {
			int i;
			this.lastFuseTime = this.currentFuseTime;
			if (this.isIgnited()) {
				this.setFuseSpeed(1);
			}
			if ((i = this.getFuseSpeed()) > 0 && this.currentFuseTime == 0) {
				this.emitGameEvent(GameEvent.PRIME_FUSE);
			}
			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}
			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				if (!(this.getHealth() < 0.01 || this.isDead()))
					this.explode();
				this.kill();
			}
		}
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		super.takeKnockback(3, x, z);
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 2.0F, false,
				World.ExplosionSourceType.NONE);
	}

	public static boolean spawning(EntityType<PossessedScientistEntity> p_223337_0_, World p_223337_1_,
			SpawnReason reason, BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		if (this.getVariant() != 3)
			this.goalSelector.add(2, new IgniteDemonGoal(this));
		if (this.getVariant() != 3)
			this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, MerchantEntity.class, 8.0F));
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	public Text getCustomName() {
		return this.getVariant() == 3 ? Text.translatable("entity.doom.screecher")
				: this.getVariant() == 2 ? Text.translatable("entity.doom.possessedengineer") : super.getCustomName();
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, 0);
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getVariant());
		if (this.dataTracker.get(CHARGED).booleanValue()) {
			tag.putBoolean("powered", true);
		}
		tag.putShort("Fuse", (short) this.fuseTime);
		tag.putByte("ExplosionRadius", (byte) this.explosionRadius);
		tag.putBoolean("ignited", this.isIgnited());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.setVariant(tag.getInt("Variant"));
		this.dataTracker.set(CHARGED, tag.getBoolean("powered"));
		if (tag.contains("Fuse", 99)) {
			this.fuseTime = tag.getShort("Fuse");
		}
		if (tag.contains("ExplosionRadius", 99)) {
			this.explosionRadius = tag.getByte("ExplosionRadius");
		}
		if (tag.getBoolean("ignited")) {
			this.ignite();
		}
	}

	public int getVariant() {
		return MathHelper.clamp((Integer) this.dataTracker.get(VARIANT), 1, 3);
	}

	public void setVariant(int variant) {
		this.dataTracker.set(VARIANT, variant);
	}

	public int getVariants() {
		return 3;
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 4);
		this.setVariant(var);
		return entityData;
	}

	public float getClientFuseTime(float timeDelta) {
		return MathHelper.lerp(timeDelta, this.lastFuseTime, this.currentFuseTime) / (float) (this.fuseTime - 2);
	}

	public int getFuseSpeed() {
		return this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean isIgnited() {
		return this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}

}
