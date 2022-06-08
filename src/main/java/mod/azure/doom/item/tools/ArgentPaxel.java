package mod.azure.doom.item.tools;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;

import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ArgentPaxel extends DiggerItem {

	private static final Set<ToolAction> PAXEL_ACTIONS = Stream
			.of(ToolActions.PICKAXE_DIG, ToolActions.AXE_DIG, ToolActions.AXE_STRIP, ToolActions.AXE_SCRAPE,
					ToolActions.AXE_WAX_OFF, ToolActions.SHOVEL_DIG, ToolActions.SHOVEL_FLATTEN)
			.collect(Collectors.toCollection(Sets::newIdentityHashSet));

	public ArgentPaxel() {
		super(8, -2.4F, DoomMod.ARGENT_TIER, BlockTags.MINEABLE_WITH_PICKAXE,
				new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1));
	}

	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
		return 30;
	}

	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		if (state.is(BlockTags.MINEABLE_WITH_PICKAXE))
			return true;
		if (state.is(BlockTags.MINEABLE_WITH_AXE))
			return true;
		if (state.is(BlockTags.MINEABLE_WITH_SHOVEL))
			return true;
		return false;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return PAXEL_ACTIONS.contains(toolAction);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		Player player = context.getPlayer();
		BlockState blockstate = level.getBlockState(blockpos);
		Optional<BlockState> optional = Optional
				.ofNullable(blockstate.getToolModifiedState(context, ToolActions.AXE_STRIP, false));
		Optional<BlockState> optional1 = Optional
				.ofNullable(blockstate.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false));
		Optional<BlockState> optional2 = Optional
				.ofNullable(blockstate.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false));
		BlockState optional3 = blockstate.getToolModifiedState(context, ToolActions.SHOVEL_FLATTEN, false);
		ItemStack itemstack = context.getItemInHand();
		Optional<BlockState> optional4 = Optional.empty();
		if (optional.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			optional4 = optional;
		} else if (optional1.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3005, blockpos, 0);
			optional4 = optional1;
		} else if (optional2.isPresent()) {
			level.playSound(player, blockpos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3004, blockpos, 0);
			optional4 = optional2;
		} else if (optional3 != null && level.isEmptyBlock(blockpos.above())) {
			level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
			optional4 = Optional.ofNullable(optional3);
		} else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
			if (!level.isClientSide()) {
				level.levelEvent((Player) null, 1009, blockpos, 0);
			}
			CampfireBlock.dowse(context.getPlayer(), level, blockpos, blockstate);
			optional4 = Optional.ofNullable(blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)));
		}
		if (optional4.isPresent()) {
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
			}
			level.setBlock(blockpos, optional4.get(), 11);
			if (player != null) {
				itemstack.hurtAndBreak(1, player, (user) -> {
					user.broadcastBreakEvent(context.getHand());
				});
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.argent_powered.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}