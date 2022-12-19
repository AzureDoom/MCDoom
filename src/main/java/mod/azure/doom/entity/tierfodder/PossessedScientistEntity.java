package mod.azure.doom.entity.tierfodder;

import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.ThrowItemGoal;
import mod.azure.doom.util.registry.DoomSounds;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PossessedScientistEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public PossessedScientistEntity(EntityType<PossessedScientistEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (this.world.isClient)
					this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
							SoundCategory.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().then("attack", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 40) {
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
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.possessed_scientist_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, DoomConfig.possessed_scientist_melee_damage)
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
		return DoomSounds.ZOMBIEMAN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ZOMBIEMAN_DEATH;
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