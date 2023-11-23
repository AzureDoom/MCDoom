package mod.azure.doom.platform;

import io.netty.buffer.Unpooled;
import mod.azure.doom.network.C2SMessageSelectCraft;
import mod.azure.doom.platform.services.DoomNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class FabricDoomNetwork implements DoomNetwork {
    @Override
    public void sendCraftingPacket(int selectedIndex) {
        C2SMessageSelectCraft.send(selectedIndex);
    }

    @Override
    public void registerClientReceiverPackets() {

    }

    @Override
    public void reloadBallista(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.BALLISTA, passedData);
    }

    @Override
    public void reloadBFG(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.BFG, passedData);
    }

    @Override
    public void reloadBFG9000(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.BFG9000, passedData);
    }

    @Override
    public void reloadChaingun(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.CHAINGUN, passedData);
    }

    @Override
    public void reloadDGauss(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.DGAUSS, passedData);
    }

    @Override
    public void reloadDPlamsa(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.DPLASMARIFLE, passedData);
    }

    @Override
    public void reloadDSG(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.DSG, passedData);
    }

    @Override
    public void reloadHeavy(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.HEAVYCANNON, passedData);
    }

    @Override
    public void reloadPistol(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.PISTOL, passedData);
    }

    @Override
    public void reloadPlasma(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.PLASMA, passedData);
    }

    @Override
    public void reloadRocket(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.ROCKETLAUNCHER, passedData);
    }

    @Override
    public void reloadSG(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.SHOTGUN, passedData);
    }

    @Override
    public void reloadSSG(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.SUPERSHOTGUN, passedData);
    }

    @Override
    public void reloadUnmaker(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.UNMAKER, passedData);
    }

    @Override
    public void reloadUnmaykr(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.UNMAYKR, passedData);
    }

    @Override
    public void reloadAxe(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.MARAUDERAXE, passedData);
    }

    @Override
    public void reloadDLCrucible(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.DARKLORDCRUCIBLE, passedData);
    }

    @Override
    public void reloadSentinel(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.SENTINELHAMMER, passedData);
    }

    @Override
    public void reloadCrucible(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.CRUCIBLE, passedData);
    }

    @Override
    public void reloadChainsaw(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.CHAINSAW, passedData);
    }

    @Override
    public void reloadChainsawA(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.CHAINSAW_ETERNAL, passedData);
    }
}
