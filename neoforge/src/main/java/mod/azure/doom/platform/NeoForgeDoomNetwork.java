package mod.azure.doom.platform;


import mod.azure.doom.MCDoom;
import mod.azure.doom.network.packets.*;
import mod.azure.doom.platform.services.DoomNetwork;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NeoForgeDoomNetwork implements DoomNetwork {
    private static final String VER = "1";
    private static final SimpleChannel PACKET_CHANNEL = NetworkRegistry.newSimpleChannel(MCDoom.modResource("main"), () -> VER, VER::equals, VER::equals);

    @Override
    public void sendCraftingPacket(int selectedIndex) {
        PACKET_CHANNEL.sendToServer(new DoomCraftingPacket(selectedIndex));
    }

    @Override
    public void registerClientReceiverPackets() {
        int id = 0;
        PACKET_CHANNEL.registerMessage(id++, DoomCraftingPacket.class, DoomCraftingPacket::encode, DoomCraftingPacket::new, DoomCraftingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, DSGLoadingPacket.class, DSGLoadingPacket::encode, DSGLoadingPacket::new, DSGLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, DGaussLoadingPacket.class, DGaussLoadingPacket::encode, DGaussLoadingPacket::new, DGaussLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, DPlasmaLoadingPacket.class, DPlasmaLoadingPacket::encode, DPlasmaLoadingPacket::new, DPlasmaLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, BallistaLoadingPacket.class, BallistaLoadingPacket::encode, BallistaLoadingPacket::new, BallistaLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, BFGLoadingPacket.class, BFGLoadingPacket::encode, BFGLoadingPacket::new, BFGLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, BFG9000LoadingPacket.class, BFG9000LoadingPacket::encode, BFG9000LoadingPacket::new, BFG9000LoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ChaingunLoadingPacket.class, ChaingunLoadingPacket::encode, ChaingunLoadingPacket::new, ChaingunLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, PistolLoadingPacket.class, PistolLoadingPacket::encode, PistolLoadingPacket::new, PistolLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, PlasmaLoadingPacket.class, PlasmaLoadingPacket::encode, PlasmaLoadingPacket::new, PlasmaLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, RocketLauncherLoadingPacket.class, RocketLauncherLoadingPacket::encode, RocketLauncherLoadingPacket::new, RocketLauncherLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, SGLoadingPacket.class, SGLoadingPacket::encode, SGLoadingPacket::new, SGLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, SSGLoadingPacket.class, SSGLoadingPacket::encode, SSGLoadingPacket::new, SSGLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, UnmaykrLoadingPacket.class, UnmaykrLoadingPacket::encode, UnmaykrLoadingPacket::new, UnmaykrLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, UnmakerLoadingPacket.class, UnmakerLoadingPacket::encode, UnmakerLoadingPacket::new, UnmakerLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, CrucibleLoadingPacket.class, CrucibleLoadingPacket::encode, CrucibleLoadingPacket::new, CrucibleLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ChainsawLoadingPacket.class, ChainsawLoadingPacket::encode, ChainsawLoadingPacket::new, ChainsawLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ChainsawEternalLoadingPacket.class, ChainsawEternalLoadingPacket::encode, ChainsawEternalLoadingPacket::new, ChainsawEternalLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, HeavyCannonLoadingPacket.class, HeavyCannonLoadingPacket::encode, HeavyCannonLoadingPacket::new, HeavyCannonLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, AxeMarauderLoadingPacket.class, AxeMarauderLoadingPacket::encode, AxeMarauderLoadingPacket::new, AxeMarauderLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, SentinelHammerLoadingPacket.class, SentinelHammerLoadingPacket::encode, SentinelHammerLoadingPacket::new, SentinelHammerLoadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, DarkLordCrucibleLoadingPacket.class, DarkLordCrucibleLoadingPacket::encode, DarkLordCrucibleLoadingPacket::new, DarkLordCrucibleLoadingPacket::handle);

    }

    @Override
    public void reloadBallista(int slot) {
        PACKET_CHANNEL.sendToServer(new BallistaLoadingPacket(slot));
    }

    @Override
    public void reloadBFG(int slot) {
        PACKET_CHANNEL.sendToServer(new BFGLoadingPacket(slot));
    }

    @Override
    public void reloadBFG9000(int slot) {
        PACKET_CHANNEL.sendToServer(new BFG9000LoadingPacket(slot));
    }

    @Override
    public void reloadChaingun(int slot) {
        PACKET_CHANNEL.sendToServer(new ChaingunLoadingPacket(slot));
    }

    @Override
    public void reloadDGauss(int slot) {
        PACKET_CHANNEL.sendToServer(new DGaussLoadingPacket(slot));
    }

    @Override
    public void reloadDPlamsa(int slot) {
        PACKET_CHANNEL.sendToServer(new DPlasmaLoadingPacket(slot));
    }

    @Override
    public void reloadDSG(int slot) {
        PACKET_CHANNEL.sendToServer(new DSGLoadingPacket(slot));
    }

    @Override
    public void reloadHeavy(int slot) {
        PACKET_CHANNEL.sendToServer(new HeavyCannonLoadingPacket(slot));
    }

    @Override
    public void reloadPistol(int slot) {
        PACKET_CHANNEL.sendToServer(new PistolLoadingPacket(slot));
    }

    @Override
    public void reloadPlasma(int slot) {
        PACKET_CHANNEL.sendToServer(new PlasmaLoadingPacket(slot));
    }

    @Override
    public void reloadRocket(int slot) {
        PACKET_CHANNEL.sendToServer(new RocketLauncherLoadingPacket(slot));
    }

    @Override
    public void reloadSG(int slot) {
        PACKET_CHANNEL.sendToServer(new SGLoadingPacket(slot));
    }

    @Override
    public void reloadSSG(int slot) {
        PACKET_CHANNEL.sendToServer(new SSGLoadingPacket(slot));
    }

    @Override
    public void reloadUnmaker(int slot) {
        PACKET_CHANNEL.sendToServer(new UnmakerLoadingPacket(slot));
    }

    @Override
    public void reloadUnmaykr(int slot) {
        PACKET_CHANNEL.sendToServer(new UnmaykrLoadingPacket(slot));
    }

    @Override
    public void reloadAxe(int slot) {
        PACKET_CHANNEL.sendToServer(new AxeMarauderLoadingPacket(slot));
    }

    @Override
    public void reloadDLCrucible(int slot) {
        PACKET_CHANNEL.sendToServer(new DarkLordCrucibleLoadingPacket(slot));
    }

    @Override
    public void reloadSentinel(int slot) {
        PACKET_CHANNEL.sendToServer(new SentinelHammerLoadingPacket(slot));
    }

    @Override
    public void reloadCrucible(int slot) {
        PACKET_CHANNEL.sendToServer(new CrucibleLoadingPacket(slot));
    }

    @Override
    public void reloadChainsaw(int slot) {
        PACKET_CHANNEL.sendToServer(new ChainsawLoadingPacket(slot));
    }

    @Override
    public void reloadChainsawA(int slot) {
        PACKET_CHANNEL.sendToServer(new ChainsawEternalLoadingPacket(slot));
    }

}
