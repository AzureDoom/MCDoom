package mod.azure.doom.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class E1M1StairsBlock extends StairsBlock {

	@SuppressWarnings("deprecation")
	public E1M1StairsBlock(BlockState state, Properties properties) {
		super(state, properties);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 15;
	}

}