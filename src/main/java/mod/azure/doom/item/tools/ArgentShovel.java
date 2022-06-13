package mod.azure.doom.item.tools;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.enums.DoomTier;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ArgentShovel extends ShovelItem {

	public ArgentShovel() {
		super(DoomTier.DOOM, 3F, -2.4F, new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1));
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(
				Text.translatable("doom.argent_powered.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

}