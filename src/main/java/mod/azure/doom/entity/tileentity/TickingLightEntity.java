package mod.azure.doom.entity.tileentity;

import mod.azure.doom.DoomMod;
import mod.azure.doom.block.TickingLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TickingLightEntity extends BlockEntity {
	private static int lifespan = 0;

	public TickingLightEntity(BlockPos blockPos, BlockState blockState) {
		super(DoomMod.TICKING_LIGHT_ENTITY, blockPos, blockState);
	}

	public void refresh(int lifeExtension) {
		lifespan = 3;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TickingLightEntity blockEntity) {
		if (lifespan++ >= 5) {
			if (level.getBlockState(blockEntity.getBlockPos()).getBlock() instanceof TickingLightBlock) {
				level.setBlockAndUpdate(blockEntity.getBlockPos(), Blocks.AIR.defaultBlockState());
				blockEntity.setRemoved();
			} else {
				level.setBlockAndUpdate(blockEntity.getBlockPos(), Blocks.AIR.defaultBlockState());
				blockEntity.setRemoved();
			}
		}
	}
}
