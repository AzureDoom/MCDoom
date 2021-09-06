package mod.azure.doom.util.registry;

import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.network.EntityPacketOnClient;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

@SuppressWarnings("deprecation")
public class ClientPacketRegister {
	public static void register() {
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
            EntityPacketOnClient.onPacket(ctx, buf);
        });
	}
}
