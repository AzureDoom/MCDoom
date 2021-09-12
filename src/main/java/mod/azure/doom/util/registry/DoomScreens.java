package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.weapons.GunTableScreenHandler;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoomScreens {

	public static final DeferredRegister<MenuType<?>> CONTAIN = DeferredRegister.create(ForgeRegistries.CONTAINERS,
			DoomMod.MODID);

	public static final RegistryObject<MenuType<GunTableScreenHandler>> SCREEN_HANDLER_TYPE = CONTAIN
			.register("gun_table_gui", () -> new MenuType<>(GunTableScreenHandler::new));

}
