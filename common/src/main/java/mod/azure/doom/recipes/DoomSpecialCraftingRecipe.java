package mod.azure.doom.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class DoomSpecialCraftingRecipe implements GunRecipes {
    private final ResourceLocation id;

    protected DoomSpecialCraftingRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }
}