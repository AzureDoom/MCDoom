package mod.azure.doom.entity.tileentity;

import mod.azure.doom.DoomMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class IconBlockEntity extends BlockEntity {

	public IconBlockEntity(BlockPos pos, BlockState state) {
		super(DoomMod.ICON, pos, state);
	}

}