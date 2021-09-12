package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.recipes.GunRecipeSerializer;
import mod.azure.doom.recipes.GunTableRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoomRecipes {

	public static final DeferredRegister<RecipeSerializer<?>> SERIAL = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, DoomMod.MODID);

	public static final RegistryObject<RecipeSerializer<?>> GUN_TABLE_RECIPE_SERIALIZER = SERIAL.register("gun_table",
			() -> new GunRecipeSerializer());

	public static final RecipeType<GunTableRecipe> MILL_RECIPE_TYPE = RecipeType
			.register(GunTableRecipe.RECIPE_TYPE_ID.toString());
}
