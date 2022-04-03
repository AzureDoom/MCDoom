package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.RangedStrafeGladiatorAttackGoal;
import mod.azure.doom.entity.attack.AbstractDoubleRangedAttack;
import mod.azure.doom.entity.attack.AttackSound;
import mod.azure.doom.entity.projectiles.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GladiatorEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final TrackedData<Integer> DEATH_STATE = DataTracker.registerData(GladiatorEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> TEXTURE = DataTracker.registerData(GladiatorEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private final ServerBossBar bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(), BossBar.Color.RED,
			BossBar.Style.NOTCHED_20)).setDarkenSky(false).setThickenFog(false);

	private AnimationFactory factory = new AnimationFactory(this);

	public GladiatorEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.dataTracker.get(DEATH_STATE) == 0 && event.isMoving() && this.dataTracker.get(STATE) < 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_phaseone", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && (this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phaseone", false));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("shield_plant", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_phasetwo", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && (this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phasetwo", false));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.velocityModified) {
			event.getController().setAnimation(new AnimationBuilder()
					.addAnimation((this.dataTracker.get(DEATH_STATE) == 0 ? "idle_phaseone" : "idle_phasetwo"), true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder()
				.addAnimation((this.dataTracker.get(DEATH_STATE) == 0 ? "idle_phaseone" : "idle_phasetwo"), true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 3
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phaseone2", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 0 && this.dataTracker.get(STATE) == 4
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phase3", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && this.dataTracker.get(STATE) == 2
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && this.dataTracker.get(STATE) == 3
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo2", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1 && this.dataTracker.get(STATE) == 4
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("melee_phasetwo2", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<GladiatorEntity> controller = new AnimationController<GladiatorEntity>(this, "controller",
				0, this::predicate);
		AnimationController<GladiatorEntity> controller1 = new AnimationController<GladiatorEntity>(this, "controller1",
				0, this::predicate1);
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
		if (event.sound.matches("plantshield")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_METAL_PLACE,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
		if (event.sound.matches("shieldtalk")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.BARON_AMBIENT,
						SoundCategory.HOSTILE, 0.25F, 1.0F, true);
			}
		}
		if (event.sound.matches("fireball")) {
			if (this.world.isClient) {
				this.getEntityWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE,
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
	public void onDeath(DamageSource source) {
		if (!this.world.isClient) {
			if (source == DamageSource.OUT_OF_WORLD) {
				this.setDeathState(1);
			}
			if (this.dataTracker.get(DEATH_STATE) == 0) {
				AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getX(),
						this.getY(), this.getZ());
				areaeffectcloudentity.setParticleType(ParticleTypes.EXPLOSION);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(55);
				areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
				this.world.spawnEntity(areaeffectcloudentity);
				this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::stop);
				this.onAttacking(this.getAttacker());
				this.world.sendEntityStatus(this, (byte) 3);
			}
			if (this.dataTracker.get(DEATH_STATE) == 1) {
				super.onDeath(source);
			}
		}
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 80 && this.dataTracker.get(DEATH_STATE) == 0) {
			this.setHealth(this.getMaxHealth());
			this.setDeathState(1);
			this.deathTime = 0;
		}
		if (this.deathTime == 40 && this.dataTracker.get(DEATH_STATE) == 1) {
			this.world.sendEntityStatus(this, (byte) 60);
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	@Override
	protected boolean shouldDropLoot() {
		return true;
	}

	public int getDeathState() {
		return this.dataTracker.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		this.dataTracker.set(DEATH_STATE, state);
	}

	public int getTextureState() {
		return this.dataTracker.get(TEXTURE);
	}

	public void setTextureState(int state) {
		this.dataTracker.set(TEXTURE, state);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Phase", this.getDeathState());
		tag.putInt("Texture", this.getTextureState());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}
		this.setDeathState(tag.getInt("Phase"));
		this.setTextureState(tag.getInt("Texture"));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DEATH_STATE, 0);
		this.dataTracker.startTracking(TEXTURE, 0);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient) {
			if (this.dataTracker.get(DEATH_STATE) == 0) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1000000, 0, false, false));
			} else {
				this.removeStatusEffect(StatusEffects.RESISTANCE);
			}
		}
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(4,
				new RangedStrafeGladiatorAttackGoal(this,
						new FireballAttack(this).setProjectileOriginOffset(0.8, 0.8, 0.8)
								.setDamage(config.gladiator_ranged_damage).setSound(SoundEvents.ITEM_FIRECHARGE_USE,
										1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F)));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, LivingEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
	}

	public class FireballAttack extends AbstractDoubleRangedAttack {

		public FireballAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
				double zOffSetModifier, float damage) {
			super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		}

		public FireballAttack(DemonEntity parentEntity) {
			super(parentEntity);
		}

		@Override
		public AttackSound getDefaultAttackSound() {
			return new AttackSound(ModSoundEvents.BALLISTA_FIRING, 1, 1);
		}

		@Override
		public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
			return new CustomFireballEntity(world, this.parentEntity, d2, d3, d4, damage);

		}

		@Override
		public ProjectileEntity getProjectile2(World world, double d2, double d3, double d4) {
			return new GladiatorMaceEntity(world, this.parentEntity, d2, d3, d4);
		}
	}

	@Override
	public boolean isImmuneToExplosion() {
		return true;
	}

	@Override
	public boolean tryAttack(Entity target) {
		this.world.sendEntityStatus(this, (byte) 4);
		boolean bl = target.damage(DamageSource.mob(this), (float) config.gladiator_melee_damage);
		if (bl) {
			target.setVelocity(target.getVelocity().add(0.4f, 0.4f, 0.4f));
			this.applyDamageEffects(this, target);
			this.world.createExplosion(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false,
					Explosion.DestructionType.BREAK);
			target.timeUntilRegen = 0;
		}
		return bl;
	}

	public boolean tryAttack1(Entity target) {
		this.world.sendEntityStatus(this, (byte) 4);
		boolean bl = target.damage(DamageSource.mob(this), (float) config.gladiator_melee_damage);
		if (bl) {
			this.applyDamageEffects(this, target);
			this.world.createExplosion(this, this.getX(), this.getY() + 5D, this.getZ(), 3.0F, false,
					Explosion.DestructionType.BREAK);
			target.timeUntilRegen = 0;
		}
		return bl;
	}

	public static boolean spawning(EntityType<GladiatorEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, config.gladiator_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, config.gladiator_melee_damage)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 50D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.BARON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.BARON_DEATH;
	}

	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		this.bossBar.addPlayer(player);
	}

	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		this.bossBar.removePlayer(player);
	}

	@Override
	public void setCustomName(@Nullable Text name) {
		super.setCustomName(name);
		this.bossBar.setName(this.getDisplayName());
	}

	@Override
	protected void mobTick() {
		super.mobTick();
		this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
	}

	@Override
	public void baseTick() {
		super.baseTick();
		float q = 50.0F;
		int k = MathHelper.floor(this.getX() - (double) q - 1.0D);
		int l = MathHelper.floor(this.getX() + (double) q + 1.0D);
		int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
		int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
		int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
		int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			if (entity instanceof GladiatorEntity && entity.age < 1) {
				entity.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

}
