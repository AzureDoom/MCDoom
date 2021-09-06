package mod.azure.doom.network;

import io.netty.buffer.Unpooled;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class C2SMessageSelectCraft implements ServerPlayNetworking.PlayChannelHandler {

	public static void send(int index) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(index);
		ClientPlayNetworking.send(PacketHandler.lock_slot, buf);
	}

	public void handle(ServerPlayerEntity player, int index) {
		ScreenHandler container = player.currentScreenHandler;
		if (container instanceof GunTableScreenHandler) {
			GunTableScreenHandler gunTableScreenHandler = (GunTableScreenHandler) container;
			gunTableScreenHandler.setRecipeIndex(index);
			gunTableScreenHandler.switchTo(index);
		}
	}

	@Override
	public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
			PacketByteBuf buf, PacketSender responseSender) {
		int index = buf.readInt();
		server.execute(() -> handle(player, index));
	}
}
