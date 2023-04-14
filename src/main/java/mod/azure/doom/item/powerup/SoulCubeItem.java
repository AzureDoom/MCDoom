package mod.azure.doom.item.powerup;

import java.util.List;

import dev.emi.trinkets.api.TrinketItem;
import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SoulCubeItem extends TrinketItem {

	public SoulCubeItem() {
		super(new Item.Properties().stacksTo(1).durability(5).tab(DoomMod.DoomPowerUPItemGroup));
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("Uses Remaining: " + (itemStack.getMaxDamage() - itemStack.getDamageValue())).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
}