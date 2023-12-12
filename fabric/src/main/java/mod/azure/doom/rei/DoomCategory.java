package mod.azure.doom.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mod.azure.doom.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DoomCategory implements DisplayCategory<DoomDisplay> {
    public static final EntryStack<ItemStack> ICON = EntryStacks.of(Services.BLOCKS_HELPER.getGunTable());

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("rei.doom.crafting");
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
        final Point startPoint = new Point(bounds.getCenterX() - 64, bounds.getCenterY() - 16);
        final Point outputPoint = new Point(startPoint.x + 100, startPoint.y + 20);
        final List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        if (display.count.get(0) > 0) {
            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x, startPoint.y - 10)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x - 3, startPoint.y - 14)).entries(
                    display.input.get(0)).disableBackground());
            widgets.add(Widgets.createLabel(
                    new Point(display.count.get(0) < 10 ? startPoint.x + 17 : startPoint.x + 13, startPoint.y + 1),
                    Component.translatable(display.count.get(0).toString())).horizontalAlignment(50));
        }

        if (display.count.get(1) > 0) {
            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 30, startPoint.y - 10)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 27, startPoint.y - 14)).entries(
                    display.input.get(1)).disableBackground().unmarkInputOrOutput());
            widgets.add(Widgets.createLabel(
                    new Point(display.count.get(1) < 10 ? startPoint.x + 46 : startPoint.x + 43, startPoint.y + 1),
                    Component.translatable(display.count.get(1).toString())));
        }

        if (display.count.get(2) > 0) {
            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 60, startPoint.y - 10)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 57, startPoint.y - 14)).entries(
                    display.input.get(2)).disableBackground());
            widgets.add(Widgets.createLabel(
                    new Point(display.count.get(2) < 10 ? startPoint.x + 76 : startPoint.x + 73, startPoint.y + 1),
                    Component.translatable(display.count.get(2).toString())));
        }

        if (display.count.get(3) > 0) {
            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x, startPoint.y + 20)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x - 3, startPoint.y + 16)).entries(
                    display.input.get(3)).disableBackground());
            widgets.add(Widgets.createLabel(
                    new Point(display.count.get(3) < 10 ? startPoint.x + 16 : startPoint.x + 14, startPoint.y + 31),
                    Component.translatable(display.count.get(3).toString())));
        }

        if (display.count.get(4) > 0) {
            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 30, startPoint.y + 20)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 27, startPoint.y + 16)).entries(
                    display.input.get(4)).disableBackground());
            widgets.add(Widgets.createLabel(
                    new Point(display.count.get(4) < 10 ? startPoint.x + 46 : startPoint.x + 43, startPoint.y + 31),
                    Component.translatable(display.count.get(4).toString())));
        }

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 70, startPoint.y + 20)));
        widgets.add(Widgets.createResultSlotBackground(outputPoint));
        widgets.add(Widgets.createSlot(outputPoint).entries(
                display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
}