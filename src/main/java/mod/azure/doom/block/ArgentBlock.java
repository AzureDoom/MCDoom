package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class ArgentBlock extends Block {

	public ArgentBlock() {
		super(FabricBlockSettings.of(Material.METAL).sounds(SoundType.METAL));
	}
}