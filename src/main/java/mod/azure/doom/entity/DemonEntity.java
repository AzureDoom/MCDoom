package mod.azure.doom.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class DemonEntity extends HostileEntity implements Angerable {

	private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(DemonEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> STATE = DataTracker.registerData(DemonEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
	private UUID targetUuid;
	private BlockPos lightBlockPos = null;

	protected DemonEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
		this.experiencePoints = (int) (this.getMaxHealth());
		stepHeight = 1.5f;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Override
	public boolean canWalkOnFluid(FluidState fluidState) {
		return fluidState.isIn(FluidTags.LAVA);
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
		this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 35) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public void attack(LivingEntity target, float pullProgress) {
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new SpiderNavigation(this, world);
	}

	@Override
	public void playAmbientSound() {
		SoundEvent soundEvent = this.getAmbientSound();
		if (soundEvent != null) {
			this.playSound(soundEvent, 0.25F, this.getSoundPitch());
		}
	}

	public void spawnLightSource(Entity entity, boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(entity.world, entity.getBlockPos(), 2);
			if (lightBlockPos == null)
				return;
			entity.world.setBlockState(lightBlockPos, DoomBlocks.TICKING_LIGHT_BLOCK.getDefaultState());
		} else if (checkDistance(lightBlockPos, entity.getBlockPos(), 2)) {
			BlockEntity blockEntity = entity.world.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(World world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(DoomBlocks.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE
				? false
				: super.damage(source, amount);
	}

}