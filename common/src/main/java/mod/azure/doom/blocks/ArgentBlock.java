package mod.azure.doom.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.ToIntFunction;

public class ArgentBlock extends Block {

    public static final IntegerProperty LIGHT_LEVEL = BlockStateProperties.AGE_15;

    public ArgentBlock() {
        super(Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.METAL).lightLevel(litBlockEmission(15)));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return lightLevel1 -> BlockStateProperties.MAX_LEVEL_15;
    }
}