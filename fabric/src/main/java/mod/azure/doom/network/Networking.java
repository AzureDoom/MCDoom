package mod.azure.doom.network;

import mod.azure.doom.items.weapons.BaseSwordItem;
import mod.azure.doom.items.weapons.DoomBaseItem;
import mod.azure.doom.platform.services.DoomNetwork;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class Networking {
    public static void registerMessages() {
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.LOCK_SLOT, new C2SMessageSelectCraft());
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.RELOAD,
                (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
                    if (!player.isCreative() && player.getMainHandItem().getItem() instanceof DoomBaseItem)
                        DoomBaseItem.reload(player, player.getUsedItemHand());
                });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.SHOOT,
                (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
                    if (player.getMainHandItem().getItem() instanceof DoomBaseItem) DoomBaseItem.shoot(player);
                });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.HOOK,
                (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
                    if (player.getMainHandItem().getItem() instanceof DoomBaseItem) DoomBaseItem.shootHook(player);
                });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.RELOAD_MELEE,
                (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
                    if (!player.isCreative() && player.getMainHandItem().getItem() instanceof BaseSwordItem)
                        BaseSwordItem.reload(player, player.getUsedItemHand());
                });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.FIREMODE,
                (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
                    if (player.getMainHandItem().getItem() instanceof DoomBaseItem)
                        DoomBaseItem.changeFireMode(player.getMainHandItem(), player);
                });
    }
}
