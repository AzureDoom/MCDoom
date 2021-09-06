package mod.azure.doom.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class E1M1StairsBlock extends StairsBlock {

	public E1M1StairsBlock(BlockState state, AbstractBlock.Settings properties) {
		super(state, properties);
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 15;
	}

}