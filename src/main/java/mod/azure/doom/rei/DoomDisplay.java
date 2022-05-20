package mod.azure.doom.rei;

import java.util.Collections;
import java.util.List;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mod.azure.doom.util.recipes.GunTableRecipe;

public class DoomDisplay implements Display {
	public final List<EntryIngredient> input;
	public final EntryIngredient output;

	public DoomDisplay(GunTableRecipe recipe) {
		input = recipe.getIngredients().stream().map(EntryIngredients::ofIngredient).toList();
		this.output = EntryIngredients.of(recipe.getOutput());
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return input;
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return Collections.singletonList(output);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return ReiPlugin.CRAFTING;
	}
}