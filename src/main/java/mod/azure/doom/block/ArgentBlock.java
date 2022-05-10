package mod.azure.doom.block;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class ArgentBlock extends Block {

	public ArgentBlock() {
		super(QuiltBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL));
	}
}