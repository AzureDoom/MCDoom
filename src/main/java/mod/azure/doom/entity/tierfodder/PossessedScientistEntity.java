package mod.azure.doom.entity.tierfodder;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.ai.goal.DemonAttackGoal;
import mod.azure.doom.util.config.Config;
import mod.azure.doom.util.config.EntityConfig;
import mod.azure.doom.util.config.EntityDefaults.EntityConfigType;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PossessedScientistEntity extends DemonEntity implements IAnimatable {

	public PossessedScientistEntity(EntityType<PossessedScientistEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public static EntityConfig config = Config.SERVER.entityConfig.get(EntityConfigType.POSSESSED_SCIENTIST);

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!(animationSpeed > -0.10F && animationSpeed < 0.10F) && !this.isAggressive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
			return PlayState.CONTINUE;
		}
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			if (level.isClientSide) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
				return PlayState.CONTINUE;
			}
		}
		if (this.isAggressive() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<PossessedScientistEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 60) {
			this.remove();
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static boolean spawning(EntityType<PossessedScientistEntity> p_223337_0_, LevelAccessor p_223337_1_,
			MobSpawnType reason, BlockPos p_223337_3_, Random p_223337_4_) {
		return passPeacefulAndYCheck(config, p_223337_1_, reason, p_223337_3_, p_223337_4_);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.goalSelector.addGoal(2, new DemonAttackGoal(this, 1.0D, false, 1));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return config.pushAttributes(Mob.createMobAttributes().add(Attributes.FOLLOW_RANGE, 25.0D));
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			@Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		float f = difficultyIn.getSpecialMultiplier();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);
		if (spawnDataIn == null) {
			spawnDataIn = new PossessedScientistEntity.GroupData(worldIn.getRandom()
					.nextFloat() < net.minecraftforge.common.ForgeConfig.SERVER.zombieBabyChance.get());
		}

		if (spawnDataIn instanceof ZombieEntity.GroupData) {
			ZombieEntity.GroupData zombieentity$groupdata = (ZombieEntity.GroupData) spawnDataIn;
			if (zombieentity$groupdata.isBaby) {
				this.setBaby(true);
			}

			this.populateDefaultEquipmentSlots(difficultyIn);
			this.populateDefaultEquipmentEnchantments(difficultyIn);
		}

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

	public class GroupData implements ILivingEntityData {
		public final boolean isChild;

		private GroupData(boolean isChildIn) {
			this.isChild = isChildIn;
		}
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
	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 7;
	}

}