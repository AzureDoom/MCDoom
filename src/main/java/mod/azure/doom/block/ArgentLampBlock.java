package mod.azure.doom.block;

import java.util.function.ToIntFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ArgentLampBlock extends Block {

	public ArgentLampBlock() {
		super(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.METAL).lightLevel(litBlockEmission()));
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return 15;
	}

	private static ToIntFunction<BlockState> litBlockEmission() {
		return (p_50763_) -> {
			return 15;
		};
	}

}