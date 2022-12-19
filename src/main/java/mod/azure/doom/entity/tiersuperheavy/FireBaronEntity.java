package mod.azure.doom.entity.tiersuperheavy;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RangedStrafeAttackGoal;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FireBaronEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public int flameTimer;

	public FireBaronEntity(EntityType<FireBaronEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
			if (event.isMoving())
				return event.setAndContinue(RawAnimation.begin().thenLoop("walking_fireborne"));
			if ((this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death_fireborne"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle_fireborne"));
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("walk"))
				if (this.world.isClient)
					this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.PINKY_STEP,
							SoundCategory.HOSTILE, 0.25F, 1.0F, false);
		})).add(new AnimationController<>(this, "attackController", 0, event -> {
			if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().then("melee_fireborne", LoopType.PLAY_ONCE));
			if (this.dataTracker.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDead()))
				return event.setAndContinue(RawAnimation.begin().then("ranged_fireborne", LoopType.PLAY_ONCE));
			return PlayState.STOP;
		}).setSoundKeyframeHandler(event -> {
			if (event.getKeyframeData().getSound().matches("attack"))
				if (this.world.isClient)
					this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.BARON_AMBIENT,
							SoundCategory.HOSTILE, 0.25F, 1.0F, false);
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void tick() {
		super.tick();
		flameTimer = (flameTimer + 1) % 3;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	public static boolean spawning(EntityType<BaronEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4, new RangedStrafeAttackGoal(this, new FireBaronEntity.FireballAttack(this)
				.setProjectileOriginOffset(0.8, 0.4, 0.8).setDamage(DoomConfig.baron_ranged_damage), 1.1D, 2));
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
			return new AttackSound(DoomSounds.PLASMA_FIRING, 1, 1);
		}

		@Override
		public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
			return new BarenBlastEntity(world, this.parentEntity, d2, d3, d4, damage);

		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.baron_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, DoomConfig.baron_melee_damage)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate localDate = LocalDate.now();
			int i = localDate.get(ChronoField.DAY_OF_MONTH);
			int j = localDate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
				this.equipStack(EquipmentSlot.HEAD,
						new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0F;
			}
		}

		return entityData;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.BARON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.BARON_DEATH;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}