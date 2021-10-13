package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.config.DoomConfig.Server;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MotherDemonEntity extends DemonEntity implements IAnimatable {

	public static Server config = DoomConfig.SERVER;

	private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(),
			BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenScreen(true).setCreateWorldFog(true);

	public MotherDemonEntity(EntityType<MotherDemonEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("moving", true));
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
			event.getController().setAnimation(new AnimationBuilder().addAnimation("shooting", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fire", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<MotherDemonEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<MotherDemonEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 40) {
			this.remove();
		}
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	protected boolean canRide(Entity p_184228_1_) {
		return false;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public ServerBossInfo getBossInfo() {
		return bossInfo;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractVillagerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtGoal(this, IronGolemEntity.class, 8.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(1, new MotherDemonEntity.AttackGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 9.05F;
	}

	static class AttackGoal extends Goal {
		private final MotherDemonEntity parentEntity;
		protected int attackTimer = 0;

		public AttackGoal(MotherDemonEntity ghast) {
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
			if (this.parentEntity.canSee(livingentity)) {
				World world = this.parentEntity.level;
				++this.attackTimer;
				Random rand = new Random();
				Vector3d vector3d = this.parentEntity.getViewVector(1.0F);
				double d0 = Math.min(livingentity.getY(), livingentity.getY());
				double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
				double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * 2.0D);
				double d3 = livingentity.getY(0.5D) - (0.5D + this.parentEntity.getY(0.5D));
				double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * 2.0D);
				float f = (float) MathHelper.atan2(livingentity.getZ() - parentEntity.getZ(),
						livingentity.getX() - parentEntity.getX());
				CustomFireballEntity fireballentity = new CustomFireballEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.SERVER.motherdemon_ranged_damage.get().floatValue());
				CustomFireballEntity fireballentity1 = new CustomFireballEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.SERVER.motherdemon_ranged_damage.get().floatValue());
				CustomFireballEntity fireballentity2 = new CustomFireballEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.SERVER.motherdemon_ranged_damage.get().floatValue());
				if (this.attackTimer == 15) {
					if (parentEntity.getHealth() <= (parentEntity.getMaxHealth() * 0.50)) {
						for (int l = 0; l < 32; ++l) {
							float f1 = f + (float) l * (float) Math.PI * 0.4F;
							for (int y = 0; y < 5; ++y) {
								parentEntity.spawnFlames(
										parentEntity.getX() + (double) MathHelper.cos(f1) * rand.nextDouble() * 11.5D,
										parentEntity.getZ() + (double) MathHelper.sin(f1) * rand.nextDouble() * 11.5D,
										d0, d1, f1, 0);
							}
							parentEntity.level.playLocalSound(this.parentEntity.getX(), this.parentEntity.getY(),
									this.parentEntity.getZ(), ModSoundEvents.MOTHER_ATTACK.get(), SoundCategory.HOSTILE,
									1.0F, 1.0F, true);
							this.parentEntity.setAttackingState(2);
						}
					} else {
						fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 1.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity);
						fireballentity1.setPos((this.parentEntity.getX() + 3) + vector3d.x * 1.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity1);
						fireballentity2.setPos((this.parentEntity.getX() - 3) + vector3d.x * 1.0D,
								this.parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 1.0D);
						world.addFreshEntity(fireballentity2);
						parentEntity.level.playLocalSound(this.parentEntity.getX(), this.parentEntity.getY(),
								this.parentEntity.getZ(), ModSoundEvents.MOTHER_ATTACK.get(), SoundCategory.HOSTILE,
								1.0F, 1.0F, true);
						this.parentEntity.setAttackingState(1);
					}
				}
				if (this.attackTimer == 30) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -50;
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
		} while (blockpos.getY() >= MathHelper.floor(maxY) - 1);

		if (flag) {
			DoomFireEntity fang = new DoomFireEntity(this.level, x, (double) blockpos.getY() + d0, z, yaw, 1, this,
					DoomConfig.SERVER.motherdemon_ranged_damage.get().floatValue());
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.motherdemon_health.get())
				.add(Attributes.ATTACK_DAMAGE, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.MOTHER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.MOTHER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.MOTHER_DEATH.get();
	}

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayerEntity player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayerEntity player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public boolean isMaxGroupSizeReached(int p_204209_1_) {
		return this.isAlive() ? true : super.isMaxGroupSizeReached(p_204209_1_);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_,
			SpawnReason p_213386_3_, ILivingEntityData p_213386_4_, CompoundNBT p_213386_5_) {
		return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		float f2 = 50.0F;
		int k1 = MathHelper.floor(this.getX() - (double) f2 - 1.0D);
		int l1 = MathHelper.floor(this.getX() + (double) f2 + 1.0D);
		int i2 = MathHelper.floor(this.getY() - (double) f2 - 1.0D);
		int i1 = MathHelper.floor(this.getY() + (double) f2 + 1.0D);
		int j2 = MathHelper.floor(this.getZ() - (double) f2 - 1.0D);
		int j1 = MathHelper.floor(this.getZ() + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			if (entity.isAddedToWorld() && entity instanceof MotherDemonEntity && entity.tickCount < 1) {
				entity.remove();
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	@Override
	public void setCustomName(ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

}
