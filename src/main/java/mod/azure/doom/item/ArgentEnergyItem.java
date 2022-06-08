package mod.azure.doom.item;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ArgentEnergyItem extends Item {

	public ArgentEnergyItem() {
		super(new Item.Properties().tab(DoomMod.DoomPowerUPItemGroup));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.argent_engery1.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.argent_engery2.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.argent_engery3.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 100000;
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	public int GetFuelValue() {
		return 1200;
	}

	@Override
	public boolean isFireResistant() {
		return true;
	}
}