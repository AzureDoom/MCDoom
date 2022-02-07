package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ClipAmmo extends Item {

	public ClipAmmo() {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup));
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("doom.bullet.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}
}