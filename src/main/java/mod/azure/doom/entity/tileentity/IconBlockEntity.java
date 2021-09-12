package mod.azure.doom.entity.tileentity;

import javax.annotation.Nullable;

import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IconBlockEntity extends BlockEntity {

	public IconBlockEntity() {
		super(ModEntityTypes.ICON.get());
	}

	@Override
	public void setChanged() {
		super.setChanged();
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 12, this.getUpdateTag());
	}

	@Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public double getViewDistance() {
		return 65536.0D;
	}

}