package mod.azure.doom.entity.tileentity;

import mod.azure.doom.block.TickingLightBlock;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TickingLightEntity extends BlockEntity {
	private int lifespan = 0;

	public TickingLightEntity(BlockPos blockPos, BlockState blockState) {
		super(ModEntityTypes.TICKING_LIGHT_ENTITY.get(), blockPos, blockState);
	}

	public void refresh(int lifeExtension) {
		lifespan = -lifeExtension;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TickingLightEntity blockEntity) {
		if (blockEntity.lifespan++ >= 5) {
			if (level.getBlockState(blockEntity.getBlockPos()).getBlock() instanceof TickingLightBlock)
				level.setBlockAndUpdate(blockEntity.getBlockPos(), Blocks.AIR.defaultBlockState());
			else
				blockEntity.setRemoved();
		}
	}
}
