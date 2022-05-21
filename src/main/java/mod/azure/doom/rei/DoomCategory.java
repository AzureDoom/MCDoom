package mod.azure.doom.rei;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mod.azure.doom.util.registry.DoomBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public class DoomCategory implements DisplayCategory<DoomDisplay> {
	public static final TranslatableComponent TITLE = new TranslatableComponent("rei.doom.crafting");
	public static final EntryStack<ItemStack> ICON = EntryStacks.of(DoomBlocks.GUN_TABLE.get());

	@Override
	public Renderer getIcon() {
		return ICON;
	}
	
	@Override
	public Component getTitle() {
		return TITLE;
	}

	@Override
	public int getMaximumDisplaysPerPage() {
		return 3;
	}

	@Override
	public int getDisplayHeight() {
		return 75;
	}

	@Override
	public CategoryIdentifier<? extends DoomDisplay> getCategoryIdentifier() {
		return ReiPlugin.CRAFTING;
	}

	@Override
	public List<Widget> setupDisplay(DoomDisplay display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 64, bounds.getCenterY() - 16);
		Point outputPoint = new Point(startPoint.x + 100, startPoint.y + 30);
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x, startPoint.y)));
		widgets.add(Widgets.createSlot(startPoint).entries(display.input.get(0)).disableBackground());

		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 30, startPoint.y)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 30, startPoint.y)).entries(display.input.get(1))
				.disableBackground());

		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 60, startPoint.y)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 60, startPoint.y)).entries(display.input.get(2))
				.disableBackground());

		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x, startPoint.y + 30)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 30)).entries(display.input.get(3))
				.disableBackground());

		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 30, startPoint.y + 30)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 30, startPoint.y + 30)).entries(display.input.get(4))
				.disableBackground());

		widgets.add(Widgets.createArrow(new Point(startPoint.x + 70, startPoint.y + 30)));
		widgets.add(Widgets.createResultSlotBackground(outputPoint));
		widgets.add(Widgets.createSlot(outputPoint).entries(display.getOutputEntries().get(0)).disableBackground()
				.markOutput());
		
        widgets.add(Widgets
                .createLabel(new Point(bounds.getMaxX() - 15, bounds.y + 5),
                        new TranslatableComponent("rei.doom.crafting.issue"))
                .rightAligned().noShadow().color(0xFF404040, 0xFFBBBBBB));
		return widgets;
	}
}