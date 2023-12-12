package mod.azure.doom.platform;


import mod.azure.doom.MCDoom;
import mod.azure.doom.network.packets.*;
import mod.azure.doom.platform.services.DoomNetwork;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

public class NeoForgeDoomNetwork implements DoomNetwork {
    private static final String VER = "1";
    private static final SimpleChannel PACKET_CHANNEL = NetworkRegistry.newSimpleChannel(MCDoom.modResource("main"),
            () -> VER, VER::equals, VER::equals);

    @Override
    public void sendCraftingPacket(int selectedIndex) {
        PACKET_CHANNEL.sendToServer(new DoomCraftingPacket(selectedIndex));
    }

    @Override
    public void registerClientReceiverPackets() {
        int id = 0;
        PACKET_CHANNEL.registerMessage(id++, DoomCraftingPacket.class, DoomCraftingPacket::encode,
                DoomCraftingPacket::new, DoomCraftingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ReloadingPacket.class, ReloadingPacket::encode, ReloadingPacket::new,
                ReloadingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, FiringPacket.class, FiringPacket::encode, FiringPacket::new,
                FiringPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, HookPacket.class, HookPacket::encode, HookPacket::new, HookPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ReloadingMeleePacket.class, ReloadingMeleePacket::encode,
                ReloadingMeleePacket::new, ReloadingMeleePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, FireModePacket.class, FireModePacket::encode,
                FireModePacket::new, FireModePacket::handle);
    }

    @Override
    public void reload(int slot) {
        PACKET_CHANNEL.sendToServer(new ReloadingPacket(slot));
    }

    @Override
    public void shoot(int slot) {
        PACKET_CHANNEL.sendToServer(new FiringPacket(slot));
    }

    @Override
    public void changeFireMode(@NotNull ItemStack stack) {
        PACKET_CHANNEL.sendToServer(new FireModePacket());
    }

    @Override
    public void hook(int slot) {
        PACKET_CHANNEL.sendToServer(new HookPacket(slot));
    }

    @Override
    public void reloadMelee(int slot) {
        PACKET_CHANNEL.sendToServer(new ReloadingMeleePacket(slot));
    }

}
