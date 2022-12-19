package mod.azure.doom.item.tools;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import mod.azure.doom.util.enums.DoomTier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ArgentPaxel extends MiningToolItem {

	protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap((new Builder<Block, BlockState>())
			.put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.getDefaultState())
			.put(Blocks.DIRT, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.PODZOL, Blocks.DIRT_PATH.getDefaultState())
			.put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.getDefaultState())
			.put(Blocks.MYCELIUM, Blocks.DIRT_PATH.getDefaultState())
			.put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.getDefaultState()).build());

	protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new Builder<Block, Block>())
			.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
			.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
			.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
			.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
			.put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
			.put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
			.put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
			.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
			.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
			.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).build();

	@SuppressWarnings("unused")
	private static final List<TagKey<Block>> MINEABLE = ImmutableList.of(BlockTags.AXE_MINEABLE,
			BlockTags.PICKAXE_MINEABLE, BlockTags.SHOVEL_MINEABLE);

	public ArgentPaxel() {
		super(9, -2.4F, DoomTier.DOOM, BlockTags.AXE_MINEABLE, new Item.Settings().maxCount(1));
	}

	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		return 30;
	}

	@Override
	public boolean isSuitableFor(BlockState state) {
		Block block = state.getBlock();
		if (block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
			return true;
		}
		Material material = state.getMaterial();
		return material == Material.STONE || material == Material.METAL;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockpos = context.getBlockPos();
		PlayerEntity player = context.getPlayer();
		BlockState blockstate = world.getBlockState(blockpos);
		BlockState resultToSet = null;
		Block strippedResult = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());
		if (strippedResult != null) {
			world.playSound(player, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			resultToSet = strippedResult.getDefaultState().with(PillarBlock.AXIS, blockstate.get(PillarBlock.AXIS));
		} else {
			if (context.getSide() == Direction.DOWN) {
				return ActionResult.PASS;
			}
			BlockState foundResult = SHOVEL_LOOKUP.get(blockstate.getBlock());
			if (foundResult != null && world.isAir(blockpos.up())) {
				world.playSound(player, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				resultToSet = foundResult;
			} else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
				resultToSet = blockstate.with(CampfireBlock.LIT, false);
			}
		}
		if (resultToSet == null) {
			return ActionResult.PASS;
		}
		if (!world.isClient) {
			world.setBlockState(blockpos, resultToSet, 11);
			if (player != null) {
				context.getStack().damage(1, (LivingEntity) player, (Consumer<LivingEntity>) ((p) -> {
					((LivingEntity) p).sendToolBreakStatus(context.getHand());
				}));
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(
				Text.translatable("doom.argent_powered.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

}