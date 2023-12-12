package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import mod.azure.doom.recipes.GunTableRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record NeoDoomRecipes() {
    public static final DeferredRegister<RecipeSerializer<?>> SERIAL = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, MCDoom.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> GUN_TABLE_RECIPE_SERIALIZER = SERIAL.register("gun_table",
            () -> new GunTableRecipe.Serializer());
}
