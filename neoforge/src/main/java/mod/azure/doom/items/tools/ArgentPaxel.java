package mod.azure.doom.items.tools;

import com.google.common.collect.Sets;
import mod.azure.doom.registry.DoomTags;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArgentPaxel extends DiggerItem {

    private static final Set<ToolAction> PAXEL_ACTIONS = Stream.of(ToolActions.PICKAXE_DIG, ToolActions.AXE_DIG,
            ToolActions.AXE_STRIP, ToolActions.AXE_SCRAPE, ToolActions.AXE_WAX_OFF, ToolActions.SHOVEL_DIG,
            ToolActions.SHOVEL_FLATTEN).collect(Collectors.toCollection(Sets::newIdentityHashSet));

    public ArgentPaxel(Tier tier) {
        super(8, -2.4F, tier, DoomTags.PAXEL_BLOCKS, new Properties().stacksTo(1));
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        return 30;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(
                BlockTags.MINEABLE_WITH_SHOVEL);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return PAXEL_ACTIONS.contains(toolAction);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final Level level = context.getLevel();
        final BlockPos blockpos = context.getClickedPos();
        final Player player = context.getPlayer();
        final BlockState blockstate = level.getBlockState(blockpos);
        final Optional<BlockState> optional = Optional.ofNullable(
                blockstate.getToolModifiedState(context, ToolActions.AXE_STRIP, false));
        final Optional<BlockState> optional1 = Optional.ofNullable(
                blockstate.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false));
        final Optional<BlockState> optional2 = Optional.ofNullable(
                blockstate.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false));
        final BlockState optional3 = blockstate.getToolModifiedState(context, ToolActions.SHOVEL_FLATTEN, false);
        final ItemStack itemstack = context.getItemInHand();
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
        } else if (blockstate.getBlock() instanceof CampfireBlock && Boolean.TRUE.equals(
                blockstate.getValue(CampfireBlock.LIT))) {
            if (!level.isClientSide()) {
                level.levelEvent((Player) null, 1009, blockpos, 0);
            }
            CampfireBlock.dowse(context.getPlayer(), level, blockpos, blockstate);
            optional4 = Optional.ofNullable(blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)));
        }
        if (optional4.isPresent()) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockpos, itemstack);
            }
            level.setBlock(blockpos, optional4.get(), 11);
            if (player != null) {
                itemstack.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(context.getHand()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("doom.argent_powered.text").withStyle(ChatFormatting.RED).withStyle(
                ChatFormatting.ITALIC));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

}