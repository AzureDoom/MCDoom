package mod.azure.doom.entity;

import java.util.Random;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig.MobStats;
import mod.azure.doom.util.packets.EntityPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.Packet;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class DemonEntity extends HostileEntity implements Angerable {

	private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(DemonEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> STATE = DataTracker.registerData(DemonEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
	private UUID targetUuid;

	public static MobStats config = DoomMod.config.stats;

	protected DemonEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreCameraFrustum = true;
		this.experiencePoints = 10;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public boolean canWalkOnFluid(Fluid fluid) {
		return fluid.isIn(FluidTags.LAVA);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	public int getAttckingState() {
		return this.dataTracker.get(STATE);
	}

	public void setAttackingState(int time) {
		this.dataTracker.set(STATE, time);
	}

	public static boolean canSpawnInDark(EntityType<? extends HostileEntity> type, ServerWorldAccess serverWorldAccess,
			SpawnReason spawnReason, BlockPos pos, Random random) {
		if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if ((spawnReason != SpawnReason.CHUNK_GENERATION && spawnReason != SpawnReason.NATURAL))
			return !serverWorldAccess.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
		return !serverWorldAccess.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ANGER_TIME, 0);
		this.dataTracker.startTracking(STATE, 0);
	}

	@Override
	public int getAngerTime() {
		return this.dataTracker.get(ANGER_TIME);
	}

	@Override
	public void setAngerTime(int ticks) {
		this.dataTracker.set(ANGER_TIME, ticks);
	}

	@Override
	public UUID getAngryAt() {
		return this.targetUuid;
	}

	@Override
	public void setAngryAt(@Nullable UUID uuid) {
		this.targetUuid = uuid;
	}

	@Override
	public void chooseRandomAngerTime() {
		this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
	}

}