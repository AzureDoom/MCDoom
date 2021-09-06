package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.recipes.GunRecipeSerializer;
import mod.azure.doom.recipes.GunTableRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoomRecipes {

	public static final DeferredRegister<IRecipeSerializer<?>> SERIAL = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, DoomMod.MODID);

	public static final RegistryObject<IRecipeSerializer<?>> GUN_TABLE_RECIPE_SERIALIZER = SERIAL.register("gun_table",
			() -> new GunRecipeSerializer());

	public static final IRecipeType<GunTableRecipe> MILL_RECIPE_TYPE = IRecipeType
			.register(GunTableRecipe.RECIPE_TYPE_ID.toString());
}
