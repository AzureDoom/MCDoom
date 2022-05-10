package mod.azure.doom.block;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class DoomSandBlock extends Block {

	public DoomSandBlock() {
		super(QuiltBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.BONE));
	}
}