package mod.azure.doom;

import mod.azure.azurelib.AzureLib;
import mod.azure.doom.config.DoomConfig;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for AzureLib!<br>
 * Hello World!<br>
 * There's not much to really see here, but feel free to stay a while and have a snack or something.
 *
 * @see mod.azure.azurelib.util.AzureLibUtil
 */
public class MCDoom {
    public static final Logger LOGGER = LogManager.getLogger(AzureLib.MOD_ID);
    public static final String MOD_ID = "doom";

    public static DoomConfig config;

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

}
