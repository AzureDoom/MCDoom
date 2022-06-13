package mod.azure.doom.entity.tierboss;

import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.IconAttackGoal;
import mod.azure.doom.entity.attack.FireballAttack;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
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
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
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

public class IconofsinEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	public static final TrackedData<Integer> DEATH_STATE = DataTracker.registerData(IconofsinEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private final ServerBossBar bossBar = (ServerBossBar) (new ServerBossBar(this.getDisplayName(),
			BossBar.Color.PURPLE, BossBar.Style.PROGRESS)).setDarkenSky(true).setThickenFog(true);
	private AnimationFactory factory = new AnimationFactory(this);

	public IconofsinEntity(EntityType<IconofsinEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && this.dataTracker.get(DEATH_STATE) == 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead()) && this.dataTracker.get(DEATH_STATE) == 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death_phaseone", false));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDead()) && this.dataTracker.get(DEATH_STATE) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && this.dataTracker.get(DEATH_STATE) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(DEATH_STATE) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving() && this.velocityModified && this.dataTracker.get(DEATH_STATE) == 1) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_nohelmet", true));
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
		if (this.dataTracker.get(STATE) == 1 && this.dataTracker.get(DEATH_STATE) == 0
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("summoned", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 2 && this.dataTracker.get(DEATH_STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("summoned_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 3 && this.dataTracker.get(DEATH_STATE) == 0
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("slam", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 4 && this.dataTracker.get(DEATH_STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("slam_nohelmet", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 5 && this.dataTracker.get(DEATH_STATE) == 0
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("stomp", true));
			return PlayState.CONTINUE;
		}
		if (this.dataTracker.get(STATE) == 6 && this.dataTracker.get(DEATH_STATE) == 1
				&& !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("stomp_nohelmet", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public int tickTimer() {
		return age;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<IconofsinEntity> controller = new AnimationController<IconofsinEntity>(this, "controller",
				0, this::predicate);
		controller.registerSoundListener(this::soundListener);
		data.addAnimationController(controller);
		data.addAnimationController(new AnimationController<IconofsinEntity>(this, "controller1", 0, this::predicate1));
	}

	private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
		if (event.sound.matches("walk")) {
			if (this.world.isClient) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), DoomSounds.CYBERDEMON_STEP,
						SoundCategory.HOSTILE, 0.25F, 1.0F, false);
			}
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
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

	public int getDeathState() {
		return this.dataTracker.get(DEATH_STATE);
	}

	public void setDeathState(int state) {
		this.dataTracker.set(DEATH_STATE, state);
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
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Phase", this.getDeathState());
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void tickCramming() {
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
		this.goalSelector.add(4,
				new IconAttackGoal(this, new FireballAttack(this, true).setProjectileOriginOffset(0.8, 0.8, 0.8)
						.setDamage(DoomConfig.icon_melee_damage
								+ (this.dataTracker.get(DEATH_STATE) == 1 ? DoomConfig.icon_phaseone_damage_boos : 0))
						.setSound(SoundEvents.ITEM_FIRECHARGE_USE, 1.0F, 1.4F + this.getRandom().nextFloat() * 0.35F),
						1.1D));
		this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	public void spawnWave(int WaveAmount, LivingEntity entity) {
		Random rand = new Random();
		List<? extends String> waveEntries = DoomConfig.icon_wave_entries;
		SplittableRandom random = new SplittableRandom();
		for (int k = 1; k < WaveAmount; ++k) {
			int r = random.nextInt(-3, 3);
			for (int i = 0; i < 1; ++i) {
				int randomIndex = rand.nextInt(waveEntries.size());
				Identifier randomElement1 = new Identifier(waveEntries.get(randomIndex));
				EntityType<?> randomElement = Registry.ENTITY_TYPE.get(randomElement1);
				Entity waveentity = randomElement.create(world);
				waveentity.refreshPositionAndAngles(entity.getX() + r, entity.getY() + 0.5D, entity.getZ() + r, 0, 0);
				world.spawnEntity(waveentity);
			}
		}
	}

	public void doDamage() {
		float q = 150.0F;
		int k = MathHelper.floor(this.getX() - (double) q - 1.0D);
		int l = MathHelper.floor(this.getX() + (double) q + 1.0D);
		int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
		int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
		int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
		int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k, (double) t, (double) v, (double) l, (double) u, (double) w));
		Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (double) (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d)) / q);
			if (y <= 1.0D) {
				if (entity instanceof LivingEntity) {
					entity.damage(DamageSource.mobProjectile(this, this.getTarget()), 7);
				}
			}
		}
	}

	public void spawnFlames(double x, double z, double maxY, double y, float yaw, int warmup) {
		BlockPos blockPos = new BlockPos(x, y, z);
		boolean bl = false;
		double d = -0.75D;
		do {
			BlockPos blockPos2 = blockPos.down();
			BlockState blockState = this.world.getBlockState(blockPos2);
			if (blockState.isSideSolidFullSquare(this.world, blockPos2, Direction.UP)) {
				if (!this.world.isAir(blockPos)) {
					BlockState blockState2 = this.world.getBlockState(blockPos);
					VoxelShape voxelShape = blockState2.getCollisionShape(this.world, blockPos);
					if (!voxelShape.isEmpty()) {
						d = voxelShape.getMax(Direction.Axis.Y);
					}
				}
				bl = true;
				break;
			}
			blockPos = blockPos.down();
		} while (blockPos.getY() >= MathHelper.floor(maxY) - 1);

		if (bl) {
			DoomFireEntity fang = new DoomFireEntity(this.world, x, (double) blockPos.getY() + d, z, yaw, warmup, this,
					DoomConfig.icon_melee_damage
							+ (this.dataTracker.get(DEATH_STATE) == 1 ? DoomConfig.icon_phaseone_damage_boos : 0));
			fang.setFireTicks(age);
			fang.isInvisible();
			fang.age = -150;
			this.world.spawnEntity(fang);
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.icon_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, DoomConfig.icon_melee_damage)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1000.0D);
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		super.takeKnockback(0, 0, 0);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
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
		return DoomSounds.ICON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DoomSounds.ICON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DoomSounds.ICON_DEATH;
	}

	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		this.bossBar.addPlayer(player);
	}

	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		this.bossBar.removePlayer(player);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DEATH_STATE, 0);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}
		this.setDeathState(tag.getInt("Phase"));
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
	public int getArmor() {
		return this.dataTracker.get(DEATH_STATE) == 1 ? 0 : (int) ((getHealth() / getMaxHealth()) * 100);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		++this.age;
		if (!this.world.isClient) {
			if (this.dataTracker.get(DEATH_STATE) == 0) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000000, 1));
			} else if (this.dataTracker.get(DEATH_STATE) == 1) {
				this.removeStatusEffect(StatusEffects.STRENGTH);
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10000000, 2));
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10000000, 1));
			}
			if (!this.world.getDimension().respawnAnchorWorks()) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10000000, 3));
				if (this.age % 2400 == 0) {
					this.heal(40F);
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		float q = 300.0F;
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
			if (entity instanceof IconofsinEntity && entity.age < 1) {
				entity.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL ? false : super.damage(source, amount);
	}

	@Override
	public boolean tryAttack(Entity target) {
		this.world.sendEntityStatus(this, (byte) 4);
		boolean bl = target.damage(DamageSource.mob(this), (float) DoomConfig.icon_melee_damage
				+ (this.dataTracker.get(DEATH_STATE) == 1 ? DoomConfig.icon_phaseone_damage_boos : 0));
		if (bl) {
			target.setVelocity(target.getVelocity().add(4.4f, 4.4f, 4.4f));
			this.applyDamageEffects(this, target);
			target.timeUntilRegen = 0;
		}
		return bl;
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Override
	public void checkDespawn() {
	}
}