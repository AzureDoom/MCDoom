package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.recipes.GunRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomRecipes {

	public static final DeferredRegister<RecipeSerializer<?>> SERIAL = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DoomMod.MODID);

	public static final RegistryObject<RecipeSerializer<?>> GUN_TABLE_RECIPE_SERIALIZER = SERIAL.register("gun_table", GunRecipeSerializer::new);

}
