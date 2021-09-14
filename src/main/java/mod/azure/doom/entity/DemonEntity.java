package mod.azure.doom.entity;

import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DemonEntity extends MonsterEntity implements IAngerable {

	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(DemonEntity.class,
			DataSerializers.INT);
	public static final DataParameter<Integer> STATE = EntityDataManager.defineId(DemonEntity.class,
			DataSerializers.INT);
	private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(20, 39);
	private UUID targetUuid;

	protected DemonEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static boolean passPeacefulAndYCheck(EntityType<? extends DemonEntity> config, IWorld world,
			SpawnReason reason, BlockPos pos, Random random) {
		// peaceful check
		if (world.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		// pass through if natural spawn and using individual spawn rules
		if ((reason != SpawnReason.CHUNK_GENERATION && reason != SpawnReason.NATURAL))
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
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.randomValue(this.random));
	}

}