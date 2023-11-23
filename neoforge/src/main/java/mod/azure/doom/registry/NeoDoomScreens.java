package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record NeoDoomScreens() {
    public static final DeferredRegister<MenuType<?>> CONTAIN = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MCDoom.MOD_ID);

    public static final RegistryObject<MenuType<GunTableScreenHandler>> SCREEN_HANDLER_TYPE = CONTAIN.register("gun_table_gui", () -> new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET));
}
