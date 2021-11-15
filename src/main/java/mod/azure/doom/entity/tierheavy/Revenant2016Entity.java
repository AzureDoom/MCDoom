package mod.azure.doom.entity.tierheavy;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Revenant2016Entity extends DemonEntity implements IAnimatable {

	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Revenant2016Entity.class,
			EntityDataSerializers.INT);

	public int flameTimer;
	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && this.isOnGround()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (this.entityData.get(STATE) == 1 || this.isNoGravity()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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

	public Revenant2016Entity(EntityType<Revenant2016Entity> entityType, Level worldIn) {
		super(entityType, worldIn);
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

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.revenant_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(1, new Revenant2016Entity.FlyingAttackGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	public int getMaxFallDistance() {
		return 99;
	}

	@Override
	protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
		return 0;
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.hasLineOfSight(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	static class FlyingAttackGoal extends Goal {
		private final Revenant2016Entity parentEntity;
		protected int attackTimer = 0;

		public FlyingAttackGoal(Revenant2016Entity ghast) {
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
			parentEntity.setNoGravity(false);
			parentEntity.push(0, 0, 0);
		}

		public void tick() {
			LivingEntity livingentity = this.parentEntity.getTarget();
			if (this.parentEntity.hasLineOfSight(livingentity)) {
				Level world = this.parentEntity.level;
				++this.attackTimer;
				Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
				double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * 2.0D);
				double d3 = livingentity.getY(0.5D) - (0.5D + this.parentEntity.getY(0.5D));
				double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * 2.0D);
				RocketMobEntity fireballentity = new RocketMobEntity(world, this.parentEntity, d2, d3, d4,
						DoomConfig.SERVER.revenant_ranged_damage.get().floatValue());
				if (this.attackTimer == 5) {
					parentEntity.setNoGravity(true);
					parentEntity.push(0, (double) 0.2F * 3.0D, 0);
					this.parentEntity.setAttackingState(1);
				}
				if (this.attackTimer == 15) {
					fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 2.0D,
							this.parentEntity.getY(0.5D) + 0.75D, fireballentity.getZ() + vector3d.z * 2.0D);
					parentEntity.level.playSound(null, parentEntity,
							(parentEntity.getVariant() == 1 ? ModSoundEvents.REVENANT_ATTACK.get()
									: ModSoundEvents.REVENANT_DOOT.get()),
							SoundSource.HOSTILE, 0.5F, 1.0F);
					world.addFreshEntity(fireballentity);
				}
				if (this.attackTimer == 20) {
					fireballentity.setPos(this.parentEntity.getX() + vector3d.x * 2.0D,
							this.parentEntity.getY(0.5D) + 0.75D, fireballentity.getZ() + vector3d.z * 2.0D);
					parentEntity.level.playSound(null, parentEntity,
							(parentEntity.getVariant() == 1 ? ModSoundEvents.REVENANT_ATTACK.get()
									: ModSoundEvents.REVENANT_DOOT.get()),
							SoundSource.HOSTILE, 0.5F, 1.0F);
					world.addFreshEntity(fireballentity);
				}
				if (this.attackTimer == 45) {
					this.parentEntity.setAttackingState(0);
					parentEntity.setNoGravity(false);
					parentEntity.push(0, 0, 0);
					this.attackTimer = -50;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.lookAt(livingentity, 30.0F, 30.0F);
		}
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.REVENANT_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.REVENANT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.REVENANT_DEATH.get();
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
}