package mod.azure.doom.network.packets;

import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FireModePacket {

    public FireModePacket() {

    }

    public FireModePacket(final FriendlyByteBuf packetBuffer) {

    }

    public static void handle(FireModePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final NetworkEvent.Context context = ctx.get();
            final PacketListener handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final ServerPlayer playerEntity = serverGamePacketListener.player;
                DoomBaseItem.changeFireMode(playerEntity.getMainHandItem(), playerEntity);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public void encode(final FriendlyByteBuf packetBuffer) {
    }
}
