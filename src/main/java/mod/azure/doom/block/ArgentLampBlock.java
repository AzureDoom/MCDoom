package mod.azure.doom.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ArgentLampBlock extends Block {

	public ArgentLampBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return 15;
	}

}