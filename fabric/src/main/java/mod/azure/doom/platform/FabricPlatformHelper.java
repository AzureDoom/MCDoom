package mod.azure.doom.platform;

import mod.azure.doom.FabricMCDoomMod;
import mod.azure.doom.platform.services.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public boolean isServerEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public RecipeSerializer<?> getRecipeSeializer() {
        return FabricMCDoomMod.GUN_TABLE_RECIPE_SERIALIZER;
    }

    @Override
    public MenuType<?> getGunScreenHandler() {
        return FabricMCDoomMod.SCREEN_HANDLER_TYPE;
    }

}