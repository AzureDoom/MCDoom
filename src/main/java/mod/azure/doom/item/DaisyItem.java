package mod.azure.doom.item;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.client.item.TooltipContext;
//import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class DaisyItem extends Item {

	public DaisyItem() {
		super(new Item.Settings().group(DoomMod.DoomPowerUPItemGroup).maxCount(1));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("doom.daisy1.text").formatted(Formatting.YELLOW).formatted(Formatting.ITALIC));
		tooltip.add(new TranslatableText("doom.daisy2.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}
}