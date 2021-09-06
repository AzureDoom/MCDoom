package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ArgentLampBlock extends Block {

	public ArgentLampBlock() {
		super(FabricBlockSettings.of(Material.REDSTONE_LAMP).sounds(BlockSoundGroup.BONE).luminance(15));
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 15;
	}

}