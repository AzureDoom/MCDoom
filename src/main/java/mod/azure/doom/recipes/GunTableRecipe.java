package mod.azure.doom.recipes;

import org.apache.commons.lang3.tuple.Pair;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.weapons.DoomGunInventory;
import mod.azure.doom.util.registry.DoomRecipes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class GunTableRecipe implements Recipe<DoomGunInventory>, Comparable<GunTableRecipe> {

	public static ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(DoomMod.MODID, "guns");
	public static final RecipeType<GunTableRecipe> GUN_TABLE = RecipeType
			.register(new ResourceLocation(DoomMod.MODID, "gun_table").toString());

	public final ResourceLocation id;
	public final Pair<Ingredient, Integer>[] ingredients;
	public final ItemStack output;

	public GunTableRecipe(ResourceLocation id, Pair<Ingredient, Integer>[] ingredients, ItemStack output) {
		this.id = id;
		this.ingredients = ingredients;
		this.output = output;
	}

	@Override
	public boolean matches(DoomGunInventory inv, Level world) {
		for (int i = 0; i < 5; i++) {
			ItemStack slotStack = inv.getItem(i);
			Pair<Ingredient, Integer> pair = ingredients[i];
			Ingredient ingredient = pair.getLeft();
			int count = pair.getRight();
			if (slotStack.getCount() < count || !(ingredient.test(slotStack))) {
				return false;
			}
		}
		return true;
	}

	public Ingredient getIngredientForSlot(int index) {
		return ingredients[index].getLeft();
	}

	public int countRequired(int index) {
		return ingredients[index].getRight();
	}

	@Override
	public ItemStack assemble(DoomGunInventory inv) {
		return this.getResultItem().copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return output;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return DoomRecipes.GUN_TABLE_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return GUN_TABLE;
	}

	@Override
	public int compareTo(GunTableRecipe o) {
		Item outputThis = getResultItem().getItem();
		Item outputOther = o.getResultItem().getItem();
		return ForgeRegistries.ITEMS.getKey(outputThis).compareTo(ForgeRegistries.ITEMS.getKey(outputOther));
	}
}
