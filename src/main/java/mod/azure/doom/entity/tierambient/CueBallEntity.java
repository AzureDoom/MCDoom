package mod.azure.doom.entity.tierambient;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.IgniteDemonGoal;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class CueBallEntity extends DemonEntity implements GeoEntity {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CueBallEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FUSE_SPEED = SynchedEntityData.defineId(CueBallEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(CueBallEntity.class,
			EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(CueBallEntity.class,
			EntityDataSerializers.BOOLEAN);
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
				return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
			if (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.cueball_health).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(3, x, z);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
			if (!this.level.isClientSide && this.getVariant() == 1)
				this.explode();
			if (!this.level.isClientSide && this.getVariant() == 3) {
				var aabb = new AABB(this.blockPosition().above()).inflate(24D, 24D, 24D);
				this.getLevel().getEntities(this, aabb).forEach(e -> {
					if (e.isAlive() && e instanceof DemonEntity)
						((LivingEntity) e).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
				});
			}
		}
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, Level.ExplosionInteraction.NONE);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		if (this.getVariant() != 3)
			this.goalSelector.addGoal(2, new IgniteDemonGoal(this));
		if (this.getVariant() != 3)
			this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
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
		if (this.level.isClientSide)
			if (this.getVariant() == 3)
				for (var i = 0; i < 2; ++i)
					this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D,
							this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
							(this.random.nextDouble() - 0.5D) * 2.0D);

		if (this.isAlive() && this.getVariant() != 3) {
			int i;
			this.lastFuseTime = this.currentFuseTime;
			if (this.isIgnited())
				this.setFuseSpeed(1);
			if ((i = this.getFuseSpeed()) > 0 && this.currentFuseTime == 0)
				this.gameEvent(GameEvent.PRIME_FUSE);
			this.currentFuseTime += i;
			if (this.currentFuseTime < 0)
				this.currentFuseTime = 0;
			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				if (!(this.getHealth() < 0.01 || this.isDeadOrDying()))
					this.explode();
				this.kill();
			}
		}
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(FUSE_SPEED, -1);
		this.entityData.define(CHARGED, false);
		this.entityData.define(IGNITED, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setVariant(tag.getInt("Variant"));
		if (this.entityData.get(CHARGED).booleanValue())
			tag.putBoolean("powered", true);
		tag.putShort("Fuse", (short) this.fuseTime);
		tag.putByte("ExplosionRadius", (byte) this.explosionRadius);
		tag.putBoolean("ignited", this.isIgnited());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
		this.entityData.set(CHARGED, tag.getBoolean("powered"));
		if (tag.contains("Fuse", 99))
			this.fuseTime = tag.getShort("Fuse");
		if (tag.contains("ExplosionRadius", 99))
			this.explosionRadius = tag.getByte("ExplosionRadius");
		if (tag.getBoolean("ignited"))
			this.ignite();
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 3);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 3;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		var var = this.getRandom().nextInt(0, 4);
		this.setVariant(var);
		return spawnDataIn;
	}

	@Override
	public Component getCustomName() {
		return this.getVariant() == 3 ? Component.translatable("entity.doom.screecher")
				: this.getVariant() == 2 ? Component.translatable("entity.doom.possessedengineer")
						: super.getCustomName();
	}

	public float getClientFuseTime(float timeDelta) {
		return Mth.lerp(timeDelta, this.lastFuseTime, this.currentFuseTime) / (float) (this.fuseTime - 2);
	}

	public int getFuseSpeed() {
		return this.entityData.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.entityData.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean isIgnited() {
		return this.entityData.get(IGNITED);
	}

	public void ignite() {
		this.entityData.set(IGNITED, true);
	}

}
