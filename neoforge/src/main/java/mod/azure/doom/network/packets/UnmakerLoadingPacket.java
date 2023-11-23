package mod.azure.doom.network.packets;

import mod.azure.doom.items.weapons.Unmaker;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UnmakerLoadingPacket {

	public int slot;

	public UnmakerLoadingPacket(int slot) {
		this.slot = slot;
	}

	public UnmakerLoadingPacket(final FriendlyByteBuf packetBuffer) {
		slot = packetBuffer.readInt();
	}

	public void encode(final FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(slot);
	}

	public static void handle(UnmakerLoadingPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			final NetworkEvent.Context context = ctx.get();
			final PacketListener handler = context.getNetworkManager().getPacketListener();
			if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
				final ServerPlayer playerEntity = serverGamePacketListener.player;
				Unmaker.reload(playerEntity, InteractionHand.MAIN_HAND);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
