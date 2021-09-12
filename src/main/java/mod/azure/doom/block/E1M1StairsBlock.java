package mod.azure.doom.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class E1M1StairsBlock extends StairBlock {

	@SuppressWarnings("deprecation")
	public E1M1StairsBlock(BlockState state, Properties properties) {
		super(state, properties);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return 15;
	}

}