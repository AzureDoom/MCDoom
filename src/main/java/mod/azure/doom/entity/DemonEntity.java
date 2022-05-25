package mod.azure.doom.entity;

import java.util.Random;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig.MobStats;
import mod.azure.doom.config.DoomConfig.Weapons;
import mod.azure.doom.network.EntityPacket;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
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
	public static MobStats config = DoomMod.config.stats;
	public static Weapons rangedconfig = DoomMod.config.weapons;

	protected DemonEntity(EntityType<? extends HostileEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreCameraFrustum = true;
		this.experiencePoints = (int) (this.getMaxHealth());
		stepHeight = 1.5f;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
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
		if (this.deathTime == 40) {
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

}