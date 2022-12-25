package mod.azure.doom.entity.tileentity;

import mod.azure.doom.DoomMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IconBlockEntity extends BlockEntity {

	public IconBlockEntity(BlockPos pos, BlockState state) {
		super(DoomMod.ICON, pos, state);
	}

}