package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.config.DoomConfig.Server;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
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

public class IconofsinEntity extends DemonEntity implements IAnimatable {

	public static Server config = DoomConfig.SERVER;

	private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(),
			BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenScreen(true).setCreateWorldFog(true);

	public IconofsinEntity(EntityType<IconofsinEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && this.getHealth() > (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && this.getHealth() < (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (this.getHealth() < (this.getMaxHealth() * 0.50)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_nohelmet", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("summoned", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("summoned_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 3 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("slam", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 4 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("slam_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 5 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("stomp", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 6 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("stomp_nohelmet", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<IconofsinEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<IconofsinEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 50) {
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
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(9, new IconofsinEntity.FireballAttackGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	class FireballAttackGoal extends Goal {
		private final IconofsinEntity parentEntity;
		protected int attackTimer = 0;

		public FireballAttackGoal(IconofsinEntity ghast) {
			this.parentEntity = ghast;
		}

		@Override
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

		@Override
		public void tick() {
			LivingEntity livingentity = this.parentEntity.getTarget();
			if (livingentity != null) {
				if (parentEntity.distanceTo(livingentity) < 10000.0D) {
					attackTimer++;
					Random rand = new Random();
					float f = (float) MathHelper.atan2(livingentity.getZ() - parentEntity.getZ(),
							livingentity.getX() - parentEntity.getX());
					if (this.attackTimer == 35) {
						SplittableRandom random = new SplittableRandom();
						int r = random.nextInt(0, 4);
						if (r == 1) {
							for (int i = 15; i < 55; ++i) {
								double d0 = Math.min(livingentity.getY(), livingentity.getY());
								double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
								float f1 = f + (float) i * (float) Math.PI * 0.4F;
								for (int y = 0; y < 5; ++y) {
									parentEntity.spawnFlames(
											parentEntity.getX()
													+ (double) MathHelper.cos(f1) * rand.nextDouble() * 11.5D,
											parentEntity.getZ()
													+ (double) MathHelper.sin(f1) * rand.nextDouble() * 11.5D,
											d0, d1, f1, 0);
								}
								if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
									this.parentEntity.setAttackingState(2);
								} else {
									this.parentEntity.setAttackingState(1);
								}
							}
						} else if (r == 2) {
							if (!parentEntity.level.isClientSide) {
								float f2 = 50.0F;
								int k1 = MathHelper.floor(parentEntity.getX() - (double) f2 - 1.0D);
								int l1 = MathHelper.floor(parentEntity.getX() + (double) f2 + 1.0D);
								int i2 = MathHelper.floor(parentEntity.getY() - (double) f2 - 1.0D);
								int i1 = MathHelper.floor(parentEntity.getY() + (double) f2 + 1.0D);
								int j2 = MathHelper.floor(parentEntity.getZ() - (double) f2 - 1.0D);
								int j1 = MathHelper.floor(parentEntity.getZ() + (double) f2 + 1.0D);
								List<Entity> list = parentEntity.level.getEntities(parentEntity, new AxisAlignedBB(
										(double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
								for (int k2 = 0; k2 < list.size(); ++k2) {
									Entity entity = list.get(k2);
									if (entity.isAlive()) {
										double d0 = (this.parentEntity.getBoundingBox().minX
												+ this.parentEntity.getBoundingBox().maxX) / 2.0D;
										double d1 = (this.parentEntity.getBoundingBox().minZ
												+ this.parentEntity.getBoundingBox().maxZ) / 2.0D;
										double d2 = entity.getX() - d0;
										double d3 = entity.getZ() - d1;
										double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
										entity.push(d2 / d4 * 10.0D, (double) 0.2F * 10.0D, d3 / d4 * 10.0D);
									}
								}
							}
							if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
								this.parentEntity.setAttackingState(6);
							} else {
								this.parentEntity.setAttackingState(5);
							}
						} else {
							parentEntity.doDamage();
							if (parentEntity.getHealth() < (parentEntity.getMaxHealth() * 0.50)) {
								this.parentEntity.setAttackingState(4);
							} else {
								this.parentEntity.setAttackingState(3);
							}
						}
					}
					if (this.attackTimer == 65) {
						this.parentEntity.setAttackingState(0);
						this.attackTimer = -75;
					}
				} else if (this.attackTimer > 0) {
					--this.attackTimer;
					this.parentEntity.setAttackingState(0);
				}
			}
		}

	}

	public void doDamage() {
		float f2 = 4.0F;
		int k1 = MathHelper.floor(this.getX() - (double) f2 - 1.0D);
		int l1 = MathHelper.floor(this.getX() + (double) f2 + 1.0D);
		int i2 = MathHelper.floor(this.getY() - (double) f2 - 1.0D);
		int i1 = MathHelper.floor(this.getY() + (double) f2 + 1.0D);
		int j2 = MathHelper.floor(this.getZ() - (double) f2 - 1.0D);
		int j1 = MathHelper.floor(this.getZ() + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vector3d vector3d = new Vector3d(this.getX(), this.getY(), this.getZ());
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			double d12 = (double) (MathHelper.sqrt(entity.distanceToSqr(vector3d)) / f2);
			if (d12 <= 1.0D) {
				if (entity instanceof LivingEntity) {
					entity.hurt(DamageSource.indirectMobAttack(this, this.getTarget()), config.icon_melee_damage.get().floatValue());
				}
			}
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
			DoomFireEntity fang = new DoomFireEntity(this.level, x, (double) blockpos.getY() + d0, z, yaw, 1, this);
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, config.icon_health.get()).add(Attributes.ATTACK_DAMAGE, 1.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 18.70F;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ICON_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ICON_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ICON_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
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

	@Override
	public int getArmorValue() {
		return (int) (getHealth() / getMaxHealth() / 1 * 9);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		++this.tickCount;
		if (!this.level.isClientSide) {
			if (this.getHealth() >= (this.getMaxHealth() * 0.50)) {
				this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 1000000, 1));
			} else {
				this.removeEffect(Effects.DAMAGE_BOOST);
				this.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 10000000, 2));
				this.addEffect(new EffectInstance(Effects.WEAKNESS, 10000000, 1));
			}
			if (!this.level.dimensionType().respawnAnchorWorks()) {
				this.setGlowing(true);
				this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 10000000, 3));
				if (this.tickCount % 2400 == 0) {
					this.heal(40F);
				}
			}
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL ? false : super.hurt(source, amount);
	}
}