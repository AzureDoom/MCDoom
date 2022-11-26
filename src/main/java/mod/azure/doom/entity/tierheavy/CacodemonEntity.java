package mod.azure.doom.entity.tierheavy;

import java.util.EnumSet;
import java.util.SplittableRandom;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStaticAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CacodemonEntity extends DemonEntity implements Enemy, IAnimatable, IAnimationTickable {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(CacodemonEntity.class,
			EntityDataSerializers.INT);

	public CacodemonEntity(EntityType<? extends CacodemonEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new CacodemonEntity.GhastMoveControl(this);
	}

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()) {
			if (level.isClientSide) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("death", EDefaultLoopTypes.PLAY_ONCE));
				return PlayState.CONTINUE;
			}
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<CacodemonEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<CacodemonEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setVariant(compound.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
	}

	public int getVariant() {
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 4);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 4;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 6);
		this.setVariant(var);
		return spawnDataIn;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static float fireBallDirectHitDamage = 6.0F;

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.FLYING_SPEED, 0.25D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.cacodemon_health.get())
				.add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.goalSelector.addGoal(7, new CacodemonEntity.LookAroundGoal(this));
		this.goalSelector.addGoal(4, new RangedStaticAttackGoal(this,
				new FireballAttack(this, true).setDamage(DoomConfig.SERVER.cacodemon_ranged_damage.get().floatValue())
						.setProjectileOriginOffset(1.5, 0.3, 1.5)
						.setSound(DoomSounds.CACODEMON_AFFECTIONATE_SCREAM.get(), 1.0F,
								1.2F / (this.getRandom().nextFloat() * 0.2F + 0.9F)),
				1));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.0F;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public void travel(Vec3 movementInput) {
		if (this.isInWater()) {
			this.moveRelative(0.02F, movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
		} else if (this.isInLava()) {
			this.moveRelative(0.02F, movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
		} else {
			BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
			float f = 0.91F;
			if (this.onGround) {
				f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
			}
			float f1 = 0.16277137F / (f * f * f);
			f = 0.91F;
			if (this.onGround) {
				f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
			}
			this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, movementInput);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
		}
		this.calculateEntityAnimation(this, false);
	}

	public boolean onClimbable() {
		return false;
	}

	static class LookAroundGoal extends Goal {
		private final CacodemonEntity parentEntity;

		public LookAroundGoal(CacodemonEntity ghast) {
			this.parentEntity = ghast;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.parentEntity.getTarget() == null) {
				Vec3 vec3d = this.parentEntity.getDeltaMovement();
				this.parentEntity.yRot = -((float) Mth.atan2(vec3d.x, vec3d.z)) * (180F / (float) Math.PI);
				this.parentEntity.yBodyRot = this.parentEntity.yRot;
			} else {
				LivingEntity livingentity = this.parentEntity.getTarget();
				if (livingentity.distanceToSqr(this.parentEntity) < 4096.0D) {
					double d1 = livingentity.getX() - this.parentEntity.getX();
					double d2 = livingentity.getZ() - this.parentEntity.getZ();
					this.parentEntity.yRot = -((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.parentEntity.yBodyRot = this.parentEntity.yRot;
				}
			}

		}
	}

	static class GhastMoveControl extends MoveControl {
		private final CacodemonEntity parentEntity;
		private int courseChangeCooldown;

		public GhastMoveControl(CacodemonEntity cacodemonEntity) {
			super(cacodemonEntity);
			this.parentEntity = cacodemonEntity;
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
					Vec3 vector3d = new Vec3(this.wantedX - this.parentEntity.getX(),
							this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
					double d0 = vector3d.length();
					vector3d = vector3d.normalize();
					if (this.canReach(vector3d, Mth.ceil(d0))) {
						this.parentEntity
								.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
					} else {
						this.operation = MoveControl.Operation.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vec3 p_220673_1_, int p_220673_2_) {
			AABB axisalignedbb = this.parentEntity.getBoundingBox();

			for (int i = 1; i < p_220673_2_; ++i) {
				axisalignedbb = axisalignedbb.move(p_220673_1_);
				if (!this.parentEntity.level.noCollision(this.parentEntity, axisalignedbb)) {
					return false;
				}
			}
			return true;
		}
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DoomSounds.CACODEMON_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.CACODEMON_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.CACODEMON_DEATH.get();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 2;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

}