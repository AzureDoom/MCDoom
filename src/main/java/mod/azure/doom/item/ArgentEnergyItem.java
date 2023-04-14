package mod.azure.doom.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ArgentEnergyItem extends Item {

	public ArgentEnergyItem() {
		super(new Item.Properties());
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("doom.argent_engery1.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		list.add(Component.translatable("doom.argent_engery2.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		list.add(Component.translatable("doom.argent_engery3.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isFireResistant() {
		return true;
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return 32767;
	}
}