package mod.azure.doom.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entity.ai.goal.DoomNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;

public class DemonEntity extends Monster implements NeutralMob, Enemy {

	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(DemonEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(DemonEntity.class, EntityDataSerializers.INT);
	private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	private UUID targetUuid;
	private BlockPos lightBlockPos = null;

	protected DemonEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		xpReward = (int) getMaxHealth();
		maxUpStep = 1.5f;
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
		return new DoomNavigation(this, world);
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

}