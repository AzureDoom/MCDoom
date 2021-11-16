package mod.azure.doom.entity.tierfodder;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.entity.ai.goal.RangedShotgunAttackGoal;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.item.ammo.ShellAmmo;
import mod.azure.doom.item.weapons.Shotgun;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ShotgunguyEntity extends DemonEntity implements RangedAttackMob, IAnimatable, IAnimationTickable {

	private final RangedShotgunAttackGoal<ShotgunguyEntity> aiArrowAttack = new RangedShotgunAttackGoal<>(this, 1.0D,
			20, 15.0F, 2);
	private final DemonAttackGoal aiAttackOnCollide = new DemonAttackGoal(this, 1.2D, false, 1) {
		public void stop() {
			super.stop();
			ShotgunguyEntity.this.setAggressive(false);
		}

		public void start() {
			super.start();
			ShotgunguyEntity.this.setAggressive(true);
		}
	};

	public ShotgunguyEntity(EntityType<ShotgunguyEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
		this.setCombatTask();
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

	private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("ranged", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<ShotgunguyEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(
				new AnimationController<ShotgunguyEntity>(this, "controller1", 0, this::predicate1));
		data.addAnimationController(
				new AnimationController<ShotgunguyEntity>(this, "controller2", 0, this::predicate2));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.shotgunguy_health.get())
				.add(Attributes.ATTACK_DAMAGE, 2.5D).add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(DoomItems.SG.get()));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setCombatTask();
	}

	@Override
	public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {
		super.setItemSlot(slotIn, stack);
		if (!this.level.isClientSide) {
			this.setCombatTask();
		}
	}

	public void setCombatTask() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.aiAttackOnCollide);
			this.goalSelector.removeGoal(this.aiArrowAttack);
			ItemStack itemstack = this
					.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof Shotgun));
			if (itemstack.getItem() instanceof Shotgun) {
				int i = 20;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					i = 20;
				}
				this.aiArrowAttack.setAttackCooldown(i);
				this.goalSelector.addGoal(4, this.aiArrowAttack);
			} else {
				this.goalSelector.addGoal(4, this.aiAttackOnCollide);
			}
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		ItemStack itemstack = this.getProjectile(
				this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof Shotgun)));
		ShotgunShellEntity abstractarrowentity = this.fireArrowa(itemstack, distanceFactor);
		if (this.getMainHandItem().getItem() instanceof Shotgun)
			abstractarrowentity = ((Shotgun) this.getMainHandItem().getItem()).customeArrow(abstractarrowentity);
		double d0 = target.getX() - this.getX();
		double d1 = target.getY(0.3333333333333333D) - abstractarrowentity.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = (double) Mth.sqrt((float) (d0 * d0 + d2 * d2));
		abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.05F, d2, 1.6F, 0.0F);
		this.playSound(ModSoundEvents.SHOTGUN_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(abstractarrowentity);
	}

	protected ShotgunShellEntity fireArrowa(ItemStack arrowStack, float distanceFactor) {
		return ShotgunguyEntity.fireArrow(this, arrowStack, distanceFactor);
	}

	public static ShotgunShellEntity fireArrow(LivingEntity shooter, ItemStack arrowStack, float distanceFactor) {
		ShellAmmo arrowitem = (ShellAmmo) (arrowStack.getItem() instanceof ShellAmmo ? arrowStack.getItem()
				: DoomItems.SHOTGUN_SHELLS.get());
		ShotgunShellEntity abstractarrowentity = arrowitem.createArrow(shooter.level, arrowStack, shooter, false);
		abstractarrowentity.setEnchantmentEffectsFromEntity(shooter, distanceFactor);
		abstractarrowentity.setBaseDamage(DoomConfig.SERVER.shotgun_damage.get());
		return abstractarrowentity;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.74F;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn,
			MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		this.populateDefaultEquipmentSlots(difficultyIn);
		this.setCombatTask();
		this.populateDefaultEquipmentEnchantments(difficultyIn);
		return spawnDataIn;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ZOMBIEMAN_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ZOMBIEMAN_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ZOMBIEMAN_DEATH.get();
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ZOMBIE_STEP;
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
	public int tickTimer() {
		return tickCount;
	}
}