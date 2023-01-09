package mod.azure.doom.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import eu.midnightdust.lib.config.MidnightConfig;
import mod.azure.doom.DoomMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuDoom implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> MidnightConfig.getScreen(parent, DoomMod.MODID);
	}
}
