package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class DoomSandBlock extends Block {

	public DoomSandBlock() {
		super(FabricBlockSettings.of(Material.DIRT).sounds(SoundType.BONE_BLOCK));
	}
}