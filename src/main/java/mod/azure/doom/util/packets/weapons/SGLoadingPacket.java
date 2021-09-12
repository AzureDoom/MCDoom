package mod.azure.doom.util.packets.weapons;

import java.util.function.Supplier;

import mod.azure.doom.item.weapons.Shotgun;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class SGLoadingPacket {

	public int slot;

	public SGLoadingPacket(int slot) {
		this.slot = slot;
	}

	public SGLoadingPacket(final FriendlyByteBuf packetBuffer) {
		this.slot = packetBuffer.readInt();
	}

	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(this.slot);
	}

	public static void handle(SGLoadingPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			NetworkEvent.Context context = ctx.get();
			PacketListener handler = context.getNetworkManager().getPacketListener();
			if (handler instanceof ServerGamePacketListenerImpl) {
				ServerPlayer playerEntity = ((ServerGamePacketListenerImpl) handler).player;
				Shotgun.reload(playerEntity, InteractionHand.MAIN_HAND);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
