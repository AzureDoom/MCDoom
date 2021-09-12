package mod.azure.doom.entity;

import java.util.Random;
import java.util.UUID;

import mod.azure.doom.util.config.EntityConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class DemonEntity extends Monster implements NeutralMob {

	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(DemonEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(DemonEntity.class,
			EntityDataSerializers.INT);
	private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	private UUID targetUuid;

	protected DemonEntity(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static boolean passPeacefulAndYCheck(EntityConfig config, LevelAccessor world, MobSpawnType reason,
			BlockPos pos, Random random) {
		// peaceful check
		if (world.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		// pass through if natural spawn and using individual spawn rules
		if ((reason != MobSpawnType.CHUNK_GENERATION && reason != MobSpawnType.NATURAL))
			return !world.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
		return !world.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
	}

	public boolean canStandOnFluid(Fluid p_230285_1_) {
		return p_230285_1_.is(FluidTags.LAVA);
	}

	public int getAttckingState() {
		return this.entityData.get(STATE);
	}

	public void setAttackingState(int time) {
		this.entityData.set(STATE, time);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGER_TIME, 0);
		this.entityData.define(STATE, 0);
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.entityData.set(ANGER_TIME, time);
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.targetUuid;
	}

	@Override
	public void setPersistentAngerTarget(UUID target) {
		this.targetUuid = target;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
	}

}