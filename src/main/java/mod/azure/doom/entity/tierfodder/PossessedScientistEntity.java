package mod.azure.doom.entity.tierfodder;

import java.util.Random;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.ThrowItemGoal;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PossessedScientistEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public PossessedScientistEntity(EntityType<PossessedScientistEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!(lastLimbDistance > -0.10F && lastLimbDistance < 0.10F)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.velocityModified) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if ((this.dataTracker.get(STATE) == 1) && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<PossessedScientistEntity> controller = new AnimationController<PossessedScientistEntity>(
				this, "controller", 0, this::predicate);
		AnimationController<PossessedScientistEntity> controller1 = new AnimationController<PossessedScientistEntity>(
				this, "controller1", 0, this::predicate1);
		controller.registerSoundListener(this::soundListener);
		controller1.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.PINKY_STEP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
		if (event.sound.matches("attack")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.EMPTY,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 80) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public static boolean spawning(EntityType<PossessedScientistEntity> p_223337_0_, World p_223337_1_,
			SpawnReason reason, BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(2, new ThrowItemGoal(this, 1.0D));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.possessed_scientist_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.possessed_scientist_melee_damage)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ZOMBIEMAN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ZOMBIEMAN_DEATH;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
		Vec3d vec3d = target.getVelocity();
		double d = target.getX() + vec3d.x - this.getX();
		double e = target.getEyeY() - (double) 1.1f - this.getY();
		double f = target.getZ() + vec3d.z - this.getZ();
		double g = Math.sqrt(d * d + f * f);
		Potion potion;
		if (target instanceof DemonEntity) {
			potion = target.getHealth() <= 4.0f ? Potions.HEALING : Potions.REGENERATION;
			this.setTarget(null);
		} else {
			potion = Potions.POISON;
		}
		PotionEntity potionEntity = new PotionEntity(this.world, this);
		potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
		potionEntity.setPitch(potionEntity.getPitch() - -20.0f);
		potionEntity.setVelocity(d, e + g * 0.2, f, 0.75f, 8.0f);
		if (!this.isSilent()) {
			this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WITCH_THROW,
					this.getSoundCategory(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
		}
		this.world.spawnEntity(potionEntity);
	}

}