package mod.azure.doom.entity.tierheavy;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.config.DoomConfig.Server;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
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
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Hellknight2016Entity extends DemonEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	public static Server config = DoomConfig.SERVER;

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isAggressive() && this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if (this.isAggressive() && animationSpeed > 0.35F && this.entityData.get(STATE) == 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
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
			event.getController().setAnimation(new AnimationBuilder().addAnimation("jumpattack", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<Hellknight2016Entity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<Hellknight2016Entity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public Hellknight2016Entity(EntityType<? extends Hellknight2016Entity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(5, new Hellknight2016Entity.AttackGoal(this, 1.5D, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	@Override
	public int getMaxFallDistance() {
		return 99;
	}

	@Override
	protected void updateControlFlags() {
		boolean flag = this.getTarget() != null && this.canSee(this.getTarget());
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		super.updateControlFlags();
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove();
		}
	}

	@Override
	protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
		return 0;
	}

	public class AttackGoal extends MeleeAttackGoal {
		private final DemonEntity entity;
		private final double speedModifier;
		private int ticksUntilNextAttack;
		private int ticksUntilNextPathRecalculation;
		private final boolean followingTargetEvenIfNotSeen;
		private double pathedTargetX;
		private double pathedTargetY;
		private double pathedTargetZ;

		public AttackGoal(DemonEntity zombieIn, double speedIn, boolean longMemoryIn) {
			super(zombieIn, speedIn, longMemoryIn);
			this.entity = zombieIn;
			this.speedModifier = speedIn;
			this.followingTargetEvenIfNotSeen = longMemoryIn;
		}

		public void start() {
			super.start();
		}

		public boolean canUse() {
			return super.canUse();
		}

		public void stop() {
			super.stop();
			this.entity.setAggressive(false);
			this.entity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
				double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
				this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
				if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().canSee(livingentity))
						&& this.ticksUntilNextPathRecalculation <= 0
						&& (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D
								|| livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY,
										this.pathedTargetZ) >= 1.0D
								|| this.mob.getRandom().nextFloat() < 0.05F)) {
					this.pathedTargetX = livingentity.getX();
					this.pathedTargetY = livingentity.getY();
					this.pathedTargetZ = livingentity.getZ();
					this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
					if (d0 > 1024.0D) {
						this.ticksUntilNextPathRecalculation += 10;
					} else if (d0 > 256.0D) {
						this.ticksUntilNextPathRecalculation += 5;
					}

					if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
						this.ticksUntilNextPathRecalculation += 15;
					}
				}

				this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 0, 0);
				this.checkAndPerformAttack(livingentity, d0);
			}
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity livingentity, double squaredDistance) {
			double d0 = this.getAttackReachSqr(livingentity);
			if (squaredDistance <= d0 && this.getTicksUntilNextAttack() <= 0) {
				this.resetAttackCooldown();
				this.mob.push(0, 1.0D, 0);
				this.entity.setAttackingState(1);
				this.mob.doHurtTarget(livingentity);
			}
		}

		@Override
		protected int getAttackInterval() {
			return 50;
		}

		@Override
		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return (double) (this.mob.getBbWidth() * 1.0F * this.mob.getBbWidth() * 1.0F + attackTarget.getBbWidth());
		}
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, config.hellknight2016_health.get())
				.add(Attributes.ATTACK_DAMAGE, config.hellknight2016_melee_damage.get())
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			@Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		float f = difficultyIn.getSpecialMultiplier();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);

		if (this.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
			LocalDate localdate = LocalDate.now();
			int i = localdate.get(ChronoField.DAY_OF_MONTH);
			int j = localdate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
				this.setItemSlot(EquipmentSlotType.HEAD,
						new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
			}
		}

		return spawnDataIn;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 2.75F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.HELLKNIGHT_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.HELLKNIGHT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.HELLKNIGHT_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ZOMBIE_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}
}