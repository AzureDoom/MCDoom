package mod.azure.doom.item;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class UnopenedItem extends Item {

	public UnopenedItem() {
		super(new Item.Properties().stacksTo(1).tab(DoomMod.DoomWeaponItemGroup));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("doom.expired.text").withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}

}