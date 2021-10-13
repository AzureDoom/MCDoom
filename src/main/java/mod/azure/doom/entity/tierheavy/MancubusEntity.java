package mod.azure.doom.entity.tierheavy;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.config.DoomConfig;

import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MancubusEntity extends DemonEntity implements IAnimatable {

	
	private int attackTimer;

	public MancubusEntity(EntityType<MancubusEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("firing", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("ground", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<MancubusEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<MancubusEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 80) {
			this.remove(RemovalReason.KILLED);
			if (level.isClientSide) {
			}
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, IronGolem.class, 8.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(1, new MancubusEntity.FireballAttackGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	static class FireballAttackGoal extends Goal {
		private final MancubusEntity parentEntity;
		protected int attackTimer = 0;

		public FireballAttackGoal(MancubusEntity ghast) {
			this.parentEntity = ghast;
		}

		public boolean canUse() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			super.start();
			this.parentEntity.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAggressive(false);
			this.parentEntity.setAttackingState(0);
			this.attackTimer = -1;
		}

		public void tick() {
			LivingEntity livingentity = this.parentEntity.getTarget();
			if (this.parentEntity.hasLineOfSight(livingentity)) {
				Level world = this.parentEntity.level;
				++this.attackTimer;
				Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
				double d0 = Math.min(livingentity.getY(), livingentity.getY());
				double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
				double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * 2.0D);
				double d3 = livingentity.getY(0.5D) - (0.5D + this.parentEntity.getY(0.5D));
				double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * 2.0D);
				float f = (float) Mth.atan2(livingentity.getZ() - parentEntity.getZ(),
						livingentity.getX() - parentEntity.getX());
				BarenBlastEntity fireballentity = new BarenBlastEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.SERVER.mancubus_ranged_damage.get().floatValue());
				if (this.attackTimer == 15) {
					if (parentEntity.distanceTo(livingentity) < 3.0D) {
						for (int i = 0; i < 5; ++i) {
							float f1 = f + (float) i * (float) Math.PI * 0.4F;
							parentEntity.spawnFlames(parentEntity.getX() + (double) Mth.cos(f1) * 1.5D,
									parentEntity.getZ() + (double) Mth.sin(f1) * 1.5D, d0, d1, f1, 0);
							this.parentEntity.setAttackingState(3);
						}
					} else if (parentEntity.distanceTo(livingentity) < 13.0D
							&& parentEntity.distanceTo(livingentity) > 3.0D) {
						for (int l = 0; l < 16; ++l) {
							double d5 = 1.25D * (double) (l + 1);
							int j = 1 * l;
							parentEntity.spawnFlames(parentEntity.getX() + (double) Mth.cos(f) * d5,
									parentEntity.getZ() + (double) Mth.sin(f) * d5, d0, d1, f, j);
							this.parentEntity.setAttackingState(2);
						}
					} else {
						fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 2.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 2.0D);
						world.addFreshEntity(fireballentity);
						this.parentEntity.setAttackingState(1);
					}
				}
				if (this.attackTimer == 20) {
					if (parentEntity.distanceTo(livingentity) <= 3.0D) {
						for (int k = 0; k < 8; ++k) {
							float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
							parentEntity.spawnFlames(parentEntity.getX() + (double) Mth.cos(f2) * 2.5D,
									parentEntity.getZ() + (double) Mth.sin(f2) * 2.5D, d0, d1, f2, 3);
						}
					} else if (parentEntity.distanceTo(livingentity) <= 13.0D
							&& parentEntity.distanceTo(livingentity) > 4.0D) {
						for (int l = 0; l < 16; ++l) {
							double d5 = 1.25D * (double) (l + 1);
							int j = 1 * l;
							parentEntity.spawnFlames(parentEntity.getX() + (double) Mth.cos(f) * d5,
									parentEntity.getZ() + (double) Mth.sin(f) * d5, d0, d1, f, j);
						}
					} else {
						fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 2.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 2.0D);
						world.addFreshEntity(fireballentity);
					}
				}
				if (this.attackTimer == 25) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -150;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.lookAt(livingentity, 30.0F, 30.0F);
		}

	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockpos = new BlockPos(x, y, z);
		boolean flag = false;
		double d0 = 0.0D;
		do {
			BlockPos blockpos1 = blockpos.below();
			BlockState blockstate = this.level.getBlockState(blockpos1);
			if (blockstate.isFaceSturdy(this.level, blockpos1, Direction.UP)) {
				if (!this.level.isEmptyBlock(blockpos)) {
					BlockState blockstate1 = this.level.getBlockState(blockpos);
					VoxelShape voxelshape = blockstate1.getCollisionShape(this.level, blockpos);
					if (!voxelshape.isEmpty()) {
						d0 = voxelshape.max(Direction.Axis.Y);
					}
				}
				flag = true;
				break;
			}
			blockpos = blockpos.below();
		} while (blockpos.getY() >= Mth.floor(maxY) - 1);

		if (flag) {
			DoomFireEntity fang = new DoomFireEntity(this.level, x, (double) blockpos.getY() + d0, z, yaw, 1, this, DoomConfig.SERVER.mancubus_ranged_damage.get().floatValue());
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.mancubus_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 2.80F;
	}

	@OnlyIn(Dist.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.MANCUBUS_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.MANCUBUS_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.MANCUBUS_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.MANCUBUS_STEP.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}
}