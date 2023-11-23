package mod.azure.doom.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.ToIntFunction;

public class ArgentLampBlock extends Block {

    public static final IntegerProperty LIGHT_LEVEL = BlockStateProperties.AGE_15;

    public ArgentLampBlock() {
        super(Properties.of().sound(SoundType.BONE_BLOCK).lightLevel(litBlockEmission(15)));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return lightLevel1 -> BlockStateProperties.MAX_LEVEL_15;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 15;
    }

}