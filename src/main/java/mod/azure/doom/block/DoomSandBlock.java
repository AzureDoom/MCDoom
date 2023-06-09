package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class DoomSandBlock extends Block {

	public DoomSandBlock() {
		super(FabricBlockSettings.of().sounds(SoundType.BONE_BLOCK));
	}
}