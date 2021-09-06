package mod.azure.doom.util.packets;

import java.util.function.Supplier;

import mod.azure.doom.client.gui.weapons.GunTableScreenHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraftforge.fml.network.NetworkEvent;

public class DoomCraftingPacket {

	private int index;

	public DoomCraftingPacket(final PacketBuffer packetBuffer) {
		this.index = packetBuffer.readInt();
	}

	public DoomCraftingPacket(int index) {
		this.index = index;
	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(index);
	}

	public void handle(Supplier<NetworkEvent.Context> sup) {
		final NetworkEvent.Context ctx = sup.get();
		ctx.enqueueWork(() -> {
			NetworkEvent.Context context = sup.get();
			INetHandler handler = context.getNetworkManager().getPacketListener();
			if (handler instanceof ServerPlayNetHandler) {
				ServerPlayerEntity playerEntity = ((ServerPlayNetHandler) handler).player;
				Container container = playerEntity.containerMenu;
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
