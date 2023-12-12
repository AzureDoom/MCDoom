package mod.azure.doom.platform.services;

import mod.azure.doom.MCDoom;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface DoomNetwork {
    public static final ResourceLocation LOCK_SLOT = MCDoom.modResource("select_craft");
    public static final ResourceLocation RELOAD = MCDoom.modResource("reload");
    public static final ResourceLocation SHOOT = MCDoom.modResource("shoot");
    public static final ResourceLocation HOOK = MCDoom.modResource("hook");
    public static final ResourceLocation FIREMODE = MCDoom.modResource("firemode");
    public static final ResourceLocation RELOAD_MELEE = MCDoom.modResource("reload_melee");

    void sendCraftingPacket(int selectedIndex);

    void registerClientReceiverPackets();

    void reload(int slot);

    void shoot(int slot);
    void changeFireMode(@NotNull ItemStack stack);

    void hook(int slot);

    void reloadMelee(int slot);
}

class LockHolder { // Package private class
    public static final Object LOCK = new Object();

    private LockHolder() {
    }
}
