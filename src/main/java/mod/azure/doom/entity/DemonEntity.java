package mod.azure.doom.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.ai.pathing.AzureNavigation;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import mod.azure.doom.entity.projectiles.entity.ChaingunMobEntity;
import mod.azure.doom.entity.projectiles.entity.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.CustomSmallFireballEntity;
import mod.azure.doom.entity.projectiles.entity.DroneBoltEntity;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import mod.azure.doom.entity.projectiles.entity.FireProjectile;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;

public abstract class DemonEntity extends Monster implements NeutralMob, Enemy, GeoEntity {

	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(DemonEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> SCREAM = SynchedEntityData.defineId(DemonEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(DemonEntity.class, EntityDataSerializers.INT);
	private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	private UUID targetUuid;
	private BlockPos lightBlockPos = null;
	public int attackstatetimer = 0;

	protected DemonEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		xpReward = (int) getMaxHealth();
		this.maxUpStep = 1.5F;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public boolean canStandOnFluid(FluidState fluidState) {
		return fluidState.is(FluidTags.LAVA);
	}

	public int getAttckingState() {
		return entityData.get(STATE);
	}

	public void setAttackingState(int time) {
		entityData.set(STATE, time);
	}

	public boolean isScreaming() {
		return this.entityData.get(SCREAM);
	}

	public void setScreamingStatus(boolean screaming) {
		this.entityData.set(SCREAM, Boolean.valueOf(screaming));
	}

	public static boolean canSpawnInDark(EntityType<? extends DemonEntity> type, LevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
		if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if (spawnReason != MobSpawnType.CHUNK_GENERATION && spawnReason != MobSpawnType.NATURAL)
			return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
		return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ANGER_TIME, 0);
		entityData.define(STATE, 0);
		entityData.define(SCREAM, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setAttackingState(compound.getInt("state"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("state", getAttckingState());
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return entityData.get(ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int ticks) {
		entityData.set(ANGER_TIME, ticks);
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return targetUuid;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		targetUuid = uuid;
	}

	@Override
	public void startPersistentAngerTimer() {
		setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(random));
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 35) {
			remove(Entity.RemovalReason.KILLED);
			dropExperience();
		}
	}

	public void performRangedAttack(LivingEntity target, float pullProgress) {
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new AzureNavigation(this, world);
	}

	@Override
	public void playAmbientSound() {
		var soundEvent = getAmbientSound();
		if (soundEvent != null)
			this.playSound(soundEvent, 0.25F, getVoicePitch());
	}

	public void spawnLightSource(Entity entity, boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(entity.level, entity.blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			entity.level.setBlockAndUpdate(lightBlockPos, AzureLibMod.TICKING_LIGHT_BLOCK.defaultBlockState());
		} else if (checkDistance(lightBlockPos, entity.blockPosition(), 2)) {
			var blockEntity = entity.level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity)
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		var offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (var i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (final var x : offsets)
			for (final var y : offsets)
				for (final var z : offsets) {
					final var offsetPos = blockPos.offset(x, y, z);
					final var state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(AzureLibMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE ? false : super.hurt(source, amount);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	public void shootBloodBolt(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new BloodBoltEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootBolt(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new DroneBoltEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootChaingun(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new ChaingunMobEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootEnergyCell(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new EnergyCellMobEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootMancubus(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new FireProjectile(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootRocket(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new RocketMobEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootFireball(Entity target, float damage, int offset) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new CustomFireballEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos((this.getX() + this.getViewVector(1.0F).x) + offset, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootBaron(Entity target, float damage, double offsetx, double offsety, double offsetz) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new BarenBlastEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2)+ offsetx, this.getTarget().getY(0.5) - (this.getY(0.5)) + offsety, this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2) + offsetz, damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x + offsetx, this.getY(0.5) + offsety, this.getZ() + this.getViewVector(1.0F).z + offsetz);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootSmallFireball(Entity target, float damage) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new CustomSmallFireballEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void shootMace(Entity target) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				var projectile = new GladiatorMaceEntity(level, this, this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2), this.getTarget().getY(0.5) - (this.getY(0.5)), this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2));
				projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z);
				this.getCommandSenderWorld().addFreshEntity(projectile);
			}
		}
	}

	public void throwPotion(LivingEntity target) {
		if (!this.level.isClientSide) {
			if (this.getTarget() != null) {
				final var d0 = target.getX() + target.getDeltaMovement().x - this.getX();
				final var d1 = target.getEyeY() - 1.1F - this.getY();
				final var d2 = target.getZ() + target.getDeltaMovement().z - this.getZ();
				final var thrownpotion = new ThrownPotion(level, this);
				thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.POISON));
				thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
				thrownpotion.shoot(d0, d1 + Math.sqrt(d0 * d0 + d2 * d2) * 0.2D, d2, 0.75F, 8.0F);
				thrownpotion.setPos(this.getX() + this.getViewVector(1.0F).x * 2, this.getY(0.5), this.getZ() + this.getViewVector(1.0F).z * 2);
				level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, getSoundSource(), 1.0F, 0.8F + random.nextFloat() * 0.4F);
				this.getCommandSenderWorld().addFreshEntity(thrownpotion);
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 10, false, false));
			}
		}
	}

}