package mod.azure.doom.item;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArgentPlateItem extends Item {

	public ArgentPlateItem() {
		super(new Item.Properties().tab(DoomMod.DoomPowerUPItemGroup).stacksTo(64));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("doom.argent_plate.text").withStyle(TextFormatting.RED)
				.withStyle(TextFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean isFireResistant() {
		return true;
	}
}