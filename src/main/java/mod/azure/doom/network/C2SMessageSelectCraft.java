package mod.azure.doom.network;

import io.netty.buffer.Unpooled;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class C2SMessageSelectCraft implements ServerPlayNetworking.PlayChannelHandler {

	public static void send(int index) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeInt(index);
		ClientPlayNetworking.send(PacketHandler.lock_slot, buf);
	}

	public void handle(ServerPlayer player, int index) {
		AbstractContainerMenu container = player.containerMenu;
		if (container instanceof GunTableScreenHandler) {
			GunTableScreenHandler gunTableScreenHandler = (GunTableScreenHandler) container;
			gunTableScreenHandler.setRecipeIndex(index);
			gunTableScreenHandler.switchTo(index);
		}
	}

	@Override
	public void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
			FriendlyByteBuf buf, PacketSender responseSender) {
		int index = buf.readInt();
		server.execute(() -> handle(player, index));
	}
}
