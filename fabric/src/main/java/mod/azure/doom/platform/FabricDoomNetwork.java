package mod.azure.doom.platform;

import io.netty.buffer.Unpooled;
import mod.azure.doom.network.C2SMessageSelectCraft;
import mod.azure.doom.platform.services.DoomNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FabricDoomNetwork implements DoomNetwork {
    @Override
    public void sendCraftingPacket(int selectedIndex) {
        C2SMessageSelectCraft.send(selectedIndex);
    }

    @Override
    public void registerClientReceiverPackets() {
    }

    @Override
    public void reload(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.RELOAD, passedData);
    }

    @Override
    public void shoot(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.SHOOT, passedData);
    }

    @Override
    public void changeFireMode(@NotNull ItemStack stack) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.FIREMODE, passedData);
    }

    @Override
    public void hook(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.HOOK, passedData);
    }

    @Override
    public void reloadMelee(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(DoomNetwork.RELOAD_MELEE, passedData);
    }
}
