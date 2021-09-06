package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class DoomSandBlock extends Block {

	public DoomSandBlock() {
		super(FabricBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.BONE));
	}
}