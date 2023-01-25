package mod.azure.doom.entity.tierfodder;

import java.util.SplittableRandom;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;

public class PossessedSoldierEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PossessedSoldierEntity.class,
			EntityDataSerializers.INT);
	public int flameTimer;

	public PossessedSoldierEntity(EntityType<PossessedSoldierEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.moveControl = new SoldierMoveControl(this);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			if (!this.isOnGround() && !this.onGround && this.getVariant() == 2
					&& !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("flying"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (this.level.isClientSide())
					this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
							SoundSource.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().then("attacking", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (this.level.isClientSide())
					this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PISTOL_HIT,
							SoundSource.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setVariant(tag.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getVariant());
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
		SplittableRandom random = new SplittableRandom();
		int var = random.nextInt(0, 4);
		this.setVariant(var);
		return spawnDataIn;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(4,
				new RangedStrafeAttackGoal(this, new PossessedSoldierEntity.FireballAttack(this)
						.setProjectileOriginOffset(0.8, 0.8, 0.8).setDamage(DoomConfig.possessed_soldier_ranged_damage),
						1.0D, 5, 30, 15, 15F, 1));
		if (this.getVariant() == 2) {
			this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		}
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public void travel(Vec3 movementInput) {
		if (this.isAggressive() && this.getVariant() == 2) {
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
					f = this.level.getBlockState(ground).getBlock().getFriction() * 0.91F;
				}
				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getBlock().getFriction() * 0.91F;
				}
				this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, movementInput);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
			}
			this.calculateEntityAnimation(this, false);
		} else {
			super.travel(movementInput);
		}
	}

	static class SoldierMoveControl extends MoveControl {
		protected final PossessedSoldierEntity entity;
		private int courseChangeCooldown;

		public SoldierMoveControl(PossessedSoldierEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public void tick() {
			if (entity.isAggressive() && this.entity.getVariant() == 2) {
				if (this.operation == MoveControl.Operation.MOVE_TO) {
					if (this.courseChangeCooldown-- <= 0) {
						this.courseChangeCooldown += this.entity.getRandom().nextInt(5) + 2;
						Vec3 vector3d = new Vec3(this.wantedX - this.entity.getX(), this.wantedY - this.entity.getY(),
								this.wantedZ - this.entity.getZ());
						double d0 = vector3d.length();
						vector3d = vector3d.normalize();
						if (this.canReach(vector3d, Mth.ceil(d0))) {
							this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(vector3d.scale(0.1D)));
						} else {
							this.operation = MoveControl.Operation.WAIT;
						}
					}
				} else {
					this.operation = MoveControl.Operation.WAIT;
					this.entity.setZza(0.0F);
				}
			} else {
				if (this.operation == MoveControl.Operation.MOVE_TO) {
					this.operation = MoveControl.Operation.WAIT;
					double d0 = this.wantedX - this.entity.getX();
					double d1 = this.wantedZ - this.entity.getZ();
					double d2 = this.wantedY - this.entity.getY();
					double d3 = d0 * d0 + d2 * d2 + d1 * d1;
					if (d3 < (double) 2.5000003E-7F) {
						this.entity.setZza(0.0F);
						return;
					}
					float f9 = (float) (Mth.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.entity.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
					this.entity.setSpeed((float) (0.25D));
					BlockPos blockpos = this.mob.blockPosition();
					BlockState blockstate = this.mob.level.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level, blockpos);
					if (d2 > (double) this.mob.getEyeHeight()
							&& d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth())
							|| !voxelshape.isEmpty()
									&& this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY()
									&& !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
						this.operation = MoveControl.Operation.JUMPING;
					}
				} else if (this.operation == MoveControl.Operation.JUMPING) {
					this.mob.setSpeed((float) (0.25D));
					if (this.mob.isOnGround()) {
						this.operation = MoveControl.Operation.WAIT;
					}
				} else {
					this.operation = MoveControl.Operation.WAIT;
					this.entity.setZza(0.0F);
				}
			}
		}

		private boolean canReach(Vec3 direction, int steps) {
			AABB axisalignedbb = this.mob.getBoundingBox();
			for (int i = 1; i < steps; ++i) {
				axisalignedbb = axisalignedbb.move(direction);
				if (!this.mob.level.noCollision(this.entity, axisalignedbb)) {
					return false;
				}
			}
			return true;
		}
	}

	protected PathNavigation createNavigation(Level worldIn) {
		FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

	public class FireballAttack extends AbstractRangedAttack {

		public FireballAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
				double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(DemonEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(DoomSounds.PLASMA_FIRING, 1, 1);
		}

		@Override
		public Projectile getProjectile(Level world, double d2, double d3, double d4) {
			return new BarenBlastEntity(world, this.parentEntity, d2, d3, d4, damage);
		}
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.possessed_soldier_health).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.74F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.PSOLDIER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.PSOLDIER_DEATH;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 2;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

}