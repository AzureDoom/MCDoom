package mod.azure.doom.item.tools;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.enums.DoomTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ArgentPaxel extends DiggerItem {

	private static final Set<ToolAction> TOOL_ACTIONS = Stream
			.of(ToolActions.AXE_DIG, ToolActions.PICKAXE_DIG, ToolActions.SHOVEL_DIG, ToolActions.AXE_SCRAPE,
					ToolActions.AXE_WAX_OFF, ToolActions.AXE_STRIP, ToolActions.SHOVEL_FLATTEN)
			.collect(Collectors.toCollection(Sets::newIdentityHashSet));

	public ArgentPaxel() {
		super(8, -2.4F, DoomTier.DOOM, BlockTags.MINEABLE_WITH_SHOVEL,
				new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1));
	}

	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
		return 30;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return TOOL_ACTIONS.contains(toolAction);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("doom.argent_powered.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}