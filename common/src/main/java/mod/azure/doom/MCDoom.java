package mod.azure.doom;

import mod.azure.doom.config.DoomConfig;
import net.minecraft.resources.ResourceLocation;

public record MCDoom() {

    public static DoomConfig config;
    public static final String MOD_ID = "doom";

    public static ResourceLocation modResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

}