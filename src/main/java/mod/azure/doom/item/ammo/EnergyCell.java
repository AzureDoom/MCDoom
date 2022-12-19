package mod.azure.doom.item.ammo;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class EnergyCell extends Item {

	public EnergyCell() {
		super(new Item.Settings());
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("doom.energycell.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

}