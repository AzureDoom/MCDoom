package mod.azure.doom.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DoomSandBlock extends Block {

    public DoomSandBlock() {
        super(Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.BONE_BLOCK));
    }
}