package mod.azure.doom.util.registry;

import mod.azure.doom.util.packets.EntityPacket;
import mod.azure.doom.util.packets.EntityPacketOnClient;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

@SuppressWarnings("deprecation")
public class ClientPacketRegister {
	public static void register() {
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
            EntityPacketOnClient.onPacket(ctx, buf);
        });
	}
}
