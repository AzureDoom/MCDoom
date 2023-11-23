package mod.azure.doom.platform.services;

import mod.azure.doom.MCDoom;
import net.minecraft.resources.ResourceLocation;

class LockHolder { // Package private class
    private LockHolder() {
    }

    public static final Object LOCK = new Object();
}

public interface DoomNetwork {
    public static final ResourceLocation LOCK_SLOT = MCDoom.modResource("select_craft");
    public static final ResourceLocation BFG = MCDoom.modResource("bfg");
    public static final ResourceLocation PISTOL = MCDoom.modResource("pistol");
    public static final ResourceLocation PLASMA = MCDoom.modResource("plamsa");
    public static final ResourceLocation BFG9000 = MCDoom.modResource("bfg9000");
    public static final ResourceLocation SHOTGUN = MCDoom.modResource("shotgun");
    public static final ResourceLocation UNMAYKR = MCDoom.modResource("unmaykr");
    public static final ResourceLocation UNMAKER = MCDoom.modResource("unmaker");
    public static final ResourceLocation BALLISTA = MCDoom.modResource("ballista");
    public static final ResourceLocation CHAINGUN = MCDoom.modResource("chaingun");
    public static final ResourceLocation CHAINSAW = MCDoom.modResource("chainsaw");
    public static final ResourceLocation CRUCIBLE = MCDoom.modResource("crucible");
    public static final ResourceLocation HEAVYCANNON = MCDoom.modResource("heavycannon");
    public static final ResourceLocation MARAUDERAXE = MCDoom.modResource("marauderaxe");
    public static final ResourceLocation SUPERSHOTGUN = MCDoom.modResource("supershotgun");
    public static final ResourceLocation GUN_TABLE_GUI = MCDoom.modResource("gun_table_gui");
    public static final ResourceLocation ROCKETLAUNCHER = MCDoom.modResource("rocketlauncher");
    public static final ResourceLocation SENTINELHAMMER = MCDoom.modResource("sentinelhammer");
    public static final ResourceLocation CHAINSAW_ETERNAL = MCDoom.modResource("chainsaweternal");
    public static final ResourceLocation DARKLORDCRUCIBLE = MCDoom.modResource("darklordcrucible");
    public static final ResourceLocation DSG = MCDoom.modResource("doomed_shotgun");
    public static final ResourceLocation DGAUSS = MCDoom.modResource("doomed_gauss");
    public static final ResourceLocation DPLASMARIFLE = MCDoom.modResource("doomed_plasma_rifle");

    void sendCraftingPacket(int selectedIndex);

    /**
     * Used to register packets that the server sends
     **/
    void registerClientReceiverPackets();

    void reloadBallista(int slot);

    void reloadBFG(int slot);

    void reloadBFG9000(int slot);

    void reloadChaingun(int slot);

    void reloadDGauss(int slot);

    void reloadDPlamsa(int slot);

    void reloadDSG(int slot);

    void reloadHeavy(int slot);

    void reloadPistol(int slot);

    void reloadPlasma(int slot);

    void reloadRocket(int slot);

    void reloadSG(int slot);

    void reloadSSG(int slot);

    void reloadUnmaker(int slot);

    void reloadUnmaykr(int slot);

    void reloadAxe(int slot);

    void reloadDLCrucible(int slot);

    void reloadSentinel(int slot);

    void reloadCrucible(int slot);

    void reloadChainsaw(int slot);

    void reloadChainsawA(int slot);
}
