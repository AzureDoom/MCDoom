package mod.azure.doom.entity.tiersuperheavy;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
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
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ArchvileEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private int targetChangeTime;
	public int flameTimer;
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ArchvileEntity.class,
			EntityDataSerializers.INT);

	public ArchvileEntity(EntityType<ArchvileEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.archvile_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (!level.isClientSide) {
			float f2 = 200.0F;
			int k1 = Mth.floor(this.getX() - (double) f2 - 1.0D);
			int l1 = Mth.floor(this.getX() + (double) f2 + 1.0D);
			int i2 = Mth.floor(this.getY() - (double) f2 - 1.0D);
			int i1 = Mth.floor(this.getY() + (double) f2 + 1.0D);
			int j2 = Mth.floor(this.getZ() - (double) f2 - 1.0D);
			int j1 = Mth.floor(this.getZ() + (double) f2 + 1.0D);
			List<Entity> list = this.level.getEntities(this,
					new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
			for (int k2 = 0; k2 < list.size(); ++k2) {
				Entity entity = list.get(k2);
				if (entity.isAlive()) {
					entity.setGlowingTag(false);
				}
			}
		}
		if (this.deathTime == 50) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropExperience();
		}
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
		if (!event.isMoving() && this.hurtMarked) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<ArchvileEntity> controller = new AnimationController<ArchvileEntity>(this, "controller", 0,
				this::predicate);
		AnimationController<ArchvileEntity> controller1 = new AnimationController<ArchvileEntity>(this, "controller1",
				0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.level.isClientSide()) {
				this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP.get(),
						SoundSource.HOSTILE, 0.25F, 1.0F, true);
			}
		}
		if (event.sound.matches("attack")) {
			if (this.level.isClientSide()) {
				this.getLevel().playLocalSound(this.getX(), this.getY(), this.getZ(),
						DoomSounds.ARCHVILE_SCREAM.get(), SoundSource.HOSTILE, 0.25F, 1.0F, true);
			}
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
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
		return Mth.clamp((Integer) this.entityData.get(VARIANT), 1, 2);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	public int getVariants() {
		return 2;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.setVariant(this.random.nextInt());
		return spawnDataIn;
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
	public boolean isBaby() {
		return false;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
		this.goalSelector.addGoal(4, new ArchvileEntity.AttackGoal(this));
		this.targetSelector.addGoal(1, new ArchvileEntity.FindPlayerGoal(this, this::isAngryAt));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	static class AttackGoal extends Goal {
		private final ArchvileEntity parentEntity;
		public int attackTimer;

		public AttackGoal(ArchvileEntity ghast) {
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
				++this.attackTimer;
				if (this.attackTimer == 20) {
					if (!this.parentEntity.level.isClientSide) {
						float f2 = 24.0F;
						int k1 = Mth.floor(this.parentEntity.getX() - (double) f2 - 1.0D);
						int l1 = Mth.floor(this.parentEntity.getX() + (double) f2 + 1.0D);
						int i2 = Mth.floor(this.parentEntity.getY() - (double) f2 - 1.0D);
						int i1 = Mth.floor(this.parentEntity.getY() + (double) f2 + 1.0D);
						int j2 = Mth.floor(this.parentEntity.getZ() - (double) f2 - 1.0D);
						int j1 = Mth.floor(this.parentEntity.getZ() + (double) f2 + 1.0D);
						List<Entity> list = this.parentEntity.level.getEntities(this.parentEntity,
								new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
						Vec3 vector3d1 = new Vec3(this.parentEntity.getX(), this.parentEntity.getY(),
								this.parentEntity.getZ());
						for (int k2 = 0; k2 < list.size(); ++k2) {
							Entity entity = list.get(k2);

							if ((entity instanceof DemonEntity)) {
								double d12 = (double) (Mth.sqrt((float) entity.distanceToSqr(vector3d1)) / f2);
								if (d12 <= 1.0D) {
									if (entity.isAlive()) {
										((DemonEntity) entity)
												.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
										entity.setGlowingTag(true);
									}
								}
							}
						}
						double d0 = Math.min(livingentity.getY(), livingentity.getY());
						double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
						float f = (float) Mth.atan2(livingentity.getZ() - parentEntity.getZ(),
								livingentity.getX() - parentEntity.getX());
						if (parentEntity.distanceToSqr(livingentity) < 9.0D
								&& parentEntity.getTarget().hasLineOfSight(livingentity)) {
							for (int i = 0; i < 15; ++i) {
								float f1 = f + (float) i * (float) Math.PI * 0.4F;
								parentEntity.spawnFangs(parentEntity.getX() + (double) Mth.cos(f1) * 1.5D,
										parentEntity.getZ() + (double) Mth.sin(f1) * 1.5D, d0, d1, f1, 0);
							}

							for (int k = 0; k < 18; ++k) {
								float f21 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
								parentEntity.spawnFangs(parentEntity.getX() + (double) Mth.cos(f21) * 2.5D,
										parentEntity.getZ() + (double) Mth.sin(f21) * 2.5D, d0, d1, f21, 3);
							}
						} else {
							for (int l = 0; l < 26; ++l) {
								double d2 = 1.25D * (double) (l + 1);
								parentEntity.spawnFangs(parentEntity.getX() + (double) Mth.cos(f) * d2,
										parentEntity.getZ() + (double) Mth.sin(f) * d2, d0, d1, f, 32);
							}
						}
					}
					if (!(this.parentEntity.level.isClientSide)) {
						this.parentEntity.playSound(DoomSounds.ARCHVILE_SCREAM.get(), 1.0F,
								1.2F / (this.parentEntity.random.nextFloat() * 0.2F + 0.9F));
					}
					this.parentEntity.setAttackingState(1);
				}
				if (this.attackTimer == 40) {
					this.parentEntity.setAttackingState(0);
					this.attackTimer = -5;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.lookAt(livingentity, 30.0F, 30.0F);
		}

	}

	static class FindPlayerGoal extends NearestAttackableTargetGoal<Player> {
		private final ArchvileEntity enderman;
		/** The player */
		private Player player;
		private int aggroTime;
		private int teleportTime;
		private final TargetingConditions startAggroTargetConditions;
		private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat()
				.ignoreLineOfSight();

		public FindPlayerGoal(ArchvileEntity p_i241912_1_, @Nullable Predicate<LivingEntity> p_i241912_2_) {
			super(p_i241912_1_, Player.class, 10, false, false, p_i241912_2_);
			this.enderman = p_i241912_1_;
			this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance())
					.selector((p_32578_) -> {
						return p_i241912_1_.isLookingAtMe((Player) p_32578_);
					});
		}

		public boolean canUse() {
			this.player = this.enderman.level.getNearestPlayer(this.startAggroTargetConditions, this.enderman);
			return this.player != null;
		}

		public void start() {
			this.aggroTime = 5;
			this.teleportTime = 0;
		}

		public void stop() {
			this.player = null;
			super.stop();
		}

		public boolean canContinueToUse() {
			if (this.player != null) {
				if (!this.enderman.isLookingAtMe(this.player)) {
					return false;
				} else {
					this.enderman.lookAt(this.player, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.target != null && this.continueAggroTargetConditions.test(this.enderman, this.target) ? true
						: super.canContinueToUse();
			}
		}

		public void tick() {
			if (this.enderman.getTarget() == null) {
				super.setTarget((LivingEntity) null);
			}

			if (this.player != null) {
				if (--this.aggroTime <= 0) {
					this.target = this.player;
					this.player = null;
					super.start();
				}
			} else {
				if (this.target != null && !this.enderman.isPassenger()) {
					if (this.enderman.isLookingAtMe((Player) this.target)) {
						if (this.target.distanceToSqr(this.enderman) < 16.0D) {
							this.enderman.teleportRandomly();
						}

						this.teleportTime = 0;
					} else if (this.target.distanceToSqr(this.enderman) > 256.0D && this.teleportTime++ >= 30
							&& this.enderman.teleportTowards(this.target)) {
						this.teleportTime = 0;
					}
				}

				super.tick();
			}

		}
	}

	private boolean teleportTowards(Entity p_70816_1_) {
		Vec3 vec3 = new Vec3(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(),
				this.getZ() - p_70816_1_.getZ());
		vec3 = vec3.normalize();
		double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.x * 10.0D;
		double d2 = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3.y * 10.0D;
		double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3.z * 10.0D;
		return this.teleport(d1, d2, d3);
	}

	private boolean isLookingAtMe(Player player) {
		Vec3 vector3d = player.getViewVector(1.0F).normalize();
		Vec3 vector3d1 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(),
				this.getZ() - player.getZ());
		double d0 = vector3d1.length();
		vector3d1 = vector3d1.normalize();
		double d1 = vector3d.dot(vector3d1);
		return d1 > 1.0D - 0.025D / d0 ? player.hasLineOfSight(this) : false;
	}

	@Override
	protected void customServerAiStep() {
		if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
			float f = this.getLightLevelDependentMagicValue();
			if (f > 0.5F && this.level.canSeeSky(this.blockPosition())
					&& this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleportRandomly();
			}
		}

		super.customServerAiStep();
	}

	protected boolean teleportRandomly() {
		if (!this.level.isClientSide() && this.isAlive()) {
			double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 10.0D;
			double d1 = this.getY() + (double) (this.random.nextInt(64) - 10);
			double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 10.0D;
			return this.teleport(d0, d1, d2);
		} else {
			return false;
		}
	}

	private boolean teleport(double p_32544_, double p_32545_, double p_32546_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);

		while (blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight()
				&& !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
			blockpos$mutableblockpos.move(Direction.DOWN);
		}

		BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
		boolean flag = blockstate.getMaterial().blocksMotion();
		boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
		if (flag && !flag1) {
			net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory
					.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
			if (event.isCanceled())
				return false;
			boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			if (flag2 && !this.isSilent()) {
				this.level.playSound((Player) null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT,
						this.getSoundSource(), 1.0F, 1.0F);
				this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}

			return flag2;
		} else {
			return false;
		}
	}

	public void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_,
			float p_190876_9_, int p_190876_10_) {
		BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
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
		} while (blockpos.getY() >= Mth.floor(p_190876_5_) - 1);

		if (flag) {
			DoomFireEntity fang = new DoomFireEntity(this.level, p_190876_1_, (double) blockpos.getY() + d0,
					p_190876_3_, p_190876_9_, 1, this, DoomConfig.SERVER.archvile_ranged_damage.get().floatValue());
			fang.setSecondsOnFire(tickCount);
			fang.setInvisible(false);
			this.level.addFreshEntity(fang);
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.ARCHVILE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ARCHVILE_DEATH.get();
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

}