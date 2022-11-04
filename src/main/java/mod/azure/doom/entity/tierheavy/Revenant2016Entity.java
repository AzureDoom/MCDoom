package mod.azure.doom.entity.tierheavy;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RandomFlyConvergeOnTargetGoal;
import mod.azure.doom.entity.ai.goal.RangedAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
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

public class Revenant2016Entity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Revenant2016Entity.class,
			EntityDataSerializers.INT);
	public int flameTimer;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public Revenant2016Entity(EntityType<Revenant2016Entity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.moveControl = new RevMoveControl(this);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && this.isOnGround()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (!this.isOnGround() && !this.onGround && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", EDefaultLoopTypes.PLAY_ONCE));
			return PlayState.CONTINUE;
		}
		if (!this.isOnGround() && !this.onGround && this.hurtMarked) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.hurtMarked) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<Revenant2016Entity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
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
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 10);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 10;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setVariant(this.random.nextInt());
		return spawnDataIn;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.revenant_health.get()).add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6f).add(Attributes.FLYING_SPEED, 0.25D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector
				.addGoal(4,
						new RangedAttackGoal(this,
								new Revenant2016Entity.FireballAttack(this).setProjectileOriginOffset(0.8, 0.8, 0.8)
										.setDamage(DoomConfig.SERVER.revenant_ranged_damage.get().floatValue()),
								1.25D, true));
		this.goalSelector.addGoal(5, new RandomFlyConvergeOnTargetGoal(this, 2, 15, 0.5));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	public class FireballAttack extends AbstractRangedAttack {

		private final Revenant2016Entity actor;

		public FireballAttack(Revenant2016Entity parentEntity, double xOffSetModifier, double entityHeightFraction,
				double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
			this.actor = parentEntity;
		}

		public FireballAttack(Revenant2016Entity parentEntity) {
			super(parentEntity);
			this.actor = parentEntity;
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(
					(actor.getVariant() == 10 ? DoomSounds.REVENANT_DOOT.get() : DoomSounds.REVENANT_ATTACK.get()), 1,
					1);
		}

		@Override
		public Projectile getProjectile(Level world, double d2, double d3, double d4) {
			return new RocketMobEntity(world, this.parentEntity, d2, d3, d4, damage);

		}
	}

	public void travel(Vec3 movementInput) {
		if (this.isAggressive()) {
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
		} else {
			super.travel(movementInput);
		}
	}

	static class RevMoveControl extends MoveControl {
		protected final DemonEntity entity;
		private int courseChangeCooldown;

		public RevMoveControl(DemonEntity entity) {
			super(entity);
			this.entity = entity;
		}

		public void tick() {
			if (entity.isAggressive()) {
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
					if (d2 > (double) this.mob.getStepHeight()
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

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.REVENANT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.REVENANT_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 8;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

}