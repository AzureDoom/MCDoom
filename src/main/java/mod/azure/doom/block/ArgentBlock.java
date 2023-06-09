package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class ArgentBlock extends Block {

	public ArgentBlock() {
		super(FabricBlockSettings.of().sounds(SoundType.METAL));
	}
}