package mod.azure.doom.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ArgentEnergyItem extends Item {

	public ArgentEnergyItem() {
		super(new Item.Settings());
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(
				Text.translatable("doom.argent_engery1.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		tooltip.add(
				Text.translatable("doom.argent_engery2.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		tooltip.add(
				Text.translatable("doom.argent_engery3.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isFireproof() {
		return true;
	}
}