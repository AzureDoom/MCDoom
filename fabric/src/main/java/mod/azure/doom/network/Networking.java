package mod.azure.doom.network;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.network.api.IPacket;
import mod.azure.azurelib.network.api.IPacketEncoder;
import mod.azure.doom.items.weapons.*;
import mod.azure.doom.platform.services.DoomNetwork;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.function.BiConsumer;

public final class Networking {

    public static final Marker MARKER = MarkerManager.getMarker("Network");

    public static void registerMessages() {
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.LOCK_SLOT, new C2SMessageSelectCraft());
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.BALLISTA, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof Ballista)
                Ballista.reload(player, player.getUsedItemHand());
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.CRUCIBLE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof SwordCrucibleItem)
                SwordCrucibleItem.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.MARAUDERAXE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof AxeMarauderItem)
                AxeMarauderItem.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.CHAINSAW_ETERNAL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof ChainsawAnimated)
                ChainsawAnimated.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.CHAINSAW, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof Chainsaw)
                Chainsaw.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.BFG, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof BFG)
                ((BFG) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.BFG9000, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof BFG9000)
                ((BFG9000) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.CHAINGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof Chaingun)
                Chaingun.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.PISTOL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof PistolItem)
                PistolItem.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.PLASMA, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof PlasmaGun)
                PlasmaGun.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.ROCKETLAUNCHER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof RocketLauncher)
                RocketLauncher.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.SHOTGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof Shotgun)
                Shotgun.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.SUPERSHOTGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof SuperShotgun)
                SuperShotgun.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.UNMAYKR, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof Unmaykr)
                Unmaykr.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.UNMAKER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof Unmaker)
                Unmaker.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.HEAVYCANNON, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof HeavyCannon)
                HeavyCannon.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.SENTINELHAMMER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof SentinelHammerItem)
                SentinelHammerItem.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.DARKLORDCRUCIBLE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof DarkLordCrucibleItem)
                DarkLordCrucibleItem.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.DSG, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof DShotgun)
                DShotgun.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.DGAUSS, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof DGauss)
                DGauss.reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(DoomNetwork.DPLASMARIFLE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof DPlasmaRifle)
                DPlasmaRifle.reload(player, InteractionHand.MAIN_HAND);
        });
    }

    public static void sendClientPacket(ServerPlayer target, mod.azure.doom.network.api.IClientPacket<?> packet) {
        dispatch(packet, (packetId, buffer) -> ServerPlayNetworking.send(target, packetId, buffer));
    }

    private static <T> void dispatch(IPacket<T> packet, BiConsumer<ResourceLocation, FriendlyByteBuf> dispatcher) {
        ResourceLocation packetId = packet.getPacketId();
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        IPacketEncoder<T> encoder = packet.getEncoder();
        T data = packet.getPacketData();
        encoder.encode(data, buffer);
        dispatcher.accept(packetId, buffer);
    }
}
