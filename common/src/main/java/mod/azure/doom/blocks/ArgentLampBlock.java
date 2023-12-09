package mod.azure.doom.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class ArgentLampBlock extends Block {

    public ArgentLampBlock() {
        super(Properties.of().sound(SoundType.BONE_BLOCK).lightLevel(litBlockEmission(15)));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return lightLevel1 -> BlockStateProperties.MAX_LEVEL_15;
    }

    @Override
    public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return 15;
    }

}