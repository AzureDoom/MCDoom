package mod.azure.doom.platform;

import mod.azure.doom.platform.services.IPlatformHelper;
import mod.azure.doom.registry.NeoDoomRecipes;
import mod.azure.doom.registry.NeoDoomScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Path getGameDir() {
        return FMLLoader.getGamePath();
    }

    @Override
    public boolean isServerEnvironment() {
        return FMLEnvironment.dist.isDedicatedServer();
    }

    @Override
    public RecipeSerializer<?> getRecipeSeializer() {
        return NeoDoomRecipes.GUN_TABLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public MenuType<?> getGunScreenHandler() {
        return NeoDoomScreens.SCREEN_HANDLER_TYPE.get();
    }

}