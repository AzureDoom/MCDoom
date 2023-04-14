package mod.azure.doom.item;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ArgentPlateItem extends Item {

	public ArgentPlateItem() {
		super(new Item.Properties().stacksTo(64).tab(DoomMod.DoomPowerUPItemGroup));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("doom.argent_plate.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}

	@Override
	public boolean isFireResistant() {
		return true;
	}
}