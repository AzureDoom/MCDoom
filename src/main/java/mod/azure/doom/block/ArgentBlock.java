package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class ArgentBlock extends Block {

	public ArgentBlock() {
		super(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE));
	}
}