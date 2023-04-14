package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomScreens {

	public static final DeferredRegister<MenuType<?>> CONTAIN = DeferredRegister.create(ForgeRegistries.MENU_TYPES, DoomMod.MODID);

	public static final RegistryObject<MenuType<GunTableScreenHandler>> SCREEN_HANDLER_TYPE = CONTAIN.register("gun_table_gui", () -> new MenuType<>(GunTableScreenHandler::new));

}
