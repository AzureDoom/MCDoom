package mod.azure.doom.entity.tiersuperheavy;

import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.ai.goal.RangedStaticAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Cyberdemon2016Entity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);

	public Cyberdemon2016Entity(EntityType<Cyberdemon2016Entity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<Cyberdemon2016Entity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<Cyberdemon2016Entity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 60) {
			this.remove(Entity.RemovalReason.KILLED);
			if (world.isClient) {
			}
		}
	}

	public static boolean spawning(EntityType<BaronEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4,
				new RangedStaticAttackGoal(this, new Cyberdemon2016Entity.FireballAttack(this)
						.setProjectileOriginOffset(0.8, 0.8, 0.8).setDamage(config.cyberdemon2016_ranged_damage), 60,
						20, 30F, 1));
		this.goalSelector.add(4, new DemonAttackGoal(this, 1.0D, false, 2));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
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
			return new AttackSound(ModSoundEvents.PLASMA_FIRING, 1, 1);
		}

		@Override
		public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
			return new RocketMobEntity(world, this.parentEntity, d2, d3, d4, damage);

		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 50D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.cyberdemon2016_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.cyberdemon2016_melee_damage)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public int getArmor() {
		return 10;
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		return super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 6.55F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.CYBERDEMON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.CYBERDEMON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.CYBERDEMON_DEATH;
	}

	protected SoundEvent getStepSound() {
		return ModSoundEvents.CYBERDEMON_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}
}