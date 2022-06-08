package mod.azure.doom.item.weapons;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ArgentSword extends SwordItem {

	public ArgentSword() {
		super(DoomMod.ARGENT_TIER, 6, -2.4F,
				new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(9000));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.argent_powered.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}