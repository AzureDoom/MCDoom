package mod.azure.doom.item.tools;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.enums.DoomTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArgentShovel extends ShovelItem {

	public ArgentShovel() {
		super(DoomTier.DOOM, 4, -2.4F, new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("doom.argent_powered.text").withStyle(TextFormatting.RED)
				.withStyle(TextFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}