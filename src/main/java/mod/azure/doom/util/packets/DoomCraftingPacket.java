package mod.azure.doom.util.packets;

import java.util.function.Supplier;

import mod.azure.doom.client.gui.weapons.GunTableScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class DoomCraftingPacket {

	private int index;

	public DoomCraftingPacket(final FriendlyByteBuf packetBuffer) {
		this.index = packetBuffer.readInt();
	}

	public DoomCraftingPacket(int index) {
		this.index = index;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(index);
	}

	public void handle(Supplier<NetworkEvent.Context> sup) {
		final NetworkEvent.Context ctx = sup.get();
		ctx.enqueueWork(() -> {
			NetworkEvent.Context context = sup.get();
			PacketListener handler = context.getNetworkManager().getPacketListener();
			if (handler instanceof ServerGamePacketListenerImpl) {
				ServerPlayer playerEntity = ((ServerGamePacketListenerImpl) handler).player;
				AbstractContainerMenu container = playerEntity.containerMenu;
				if (container instanceof GunTableScreenHandler) {
					GunTableScreenHandler gunTableScreenHandler = (GunTableScreenHandler) container;
					gunTableScreenHandler.setRecipeIndex(index);
					gunTableScreenHandler.switchTo(index);
				}
			}
		});
		ctx.setPacketHandled(true);
	}

}
