package mod.azure.doom.block;

import javax.annotation.Nullable;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tileentity.IconBlockEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class DoomWallBlock extends ContainerBlock {

	public static final BooleanProperty light = RedstoneTorchBlock.LIT;

	@Nullable
	private static BlockPattern iconPatternFull;

	public DoomWallBlock(Block.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(light, Boolean.valueOf(true)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(light);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 15;
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof IconBlockEntity) {
			checkIconSpawn(worldIn, pos, (IconBlockEntity) tileentity);
		}
	}

	public static void checkIconSpawn(World worldIn, BlockPos pos, IconBlockEntity tileEntityIn) {
		if (!worldIn.isClientSide) {
			Block block = tileEntityIn.getBlockState().getBlock();
			boolean flag = block == DoomBlocks.DOOM_WALL1.get() || block == DoomBlocks.DOOM_WALL2.get()
					|| block == DoomBlocks.DOOM_WALL3.get() || block == DoomBlocks.DOOM_WALL4.get()
					|| block == DoomBlocks.DOOM_WALL5.get() || block == DoomBlocks.DOOM_WALL6.get()
					|| block == DoomBlocks.DOOM_WALL7.get() || block == DoomBlocks.DOOM_WALL8.get()
					|| block == DoomBlocks.DOOM_WALL9.get() || block == DoomBlocks.DOOM_WALL10.get()
					|| block == DoomBlocks.DOOM_WALL11.get() || block == DoomBlocks.DOOM_WALL12.get()
					|| block == DoomBlocks.DOOM_WALL13.get() || block == DoomBlocks.DOOM_WALL14.get()
					|| block == DoomBlocks.DOOM_WALL15.get() || block == DoomBlocks.DOOM_WALL16.get();
			if (flag && pos.getY() >= 3 && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern blockpattern = getOrCreateIconFull();
				BlockPattern.PatternHelper blockpattern$patternhelper = blockpattern.find(worldIn, pos);
				if (blockpattern$patternhelper != null) {
					for (int i = 0; i < blockpattern.getWidth(); ++i) {
						for (int j = 0; j < blockpattern.getHeight(); ++j) {
							CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.getBlock(i, j, 0);
							worldIn.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
							worldIn.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
						}
					}

					IconofsinEntity witherentity = ModEntityTypes.ICONOFSIN.get().create(worldIn);
					BlockPos blockpos = blockpattern$patternhelper.getBlock(1, 2, 0).getPos();
					witherentity.moveTo((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.55D,
							(double) blockpos.getZ() + 0.5D,
							blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F,
							0.0F);
					witherentity.yBodyRot = blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X
							? 0.0F
							: 90.0F;
					witherentity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 4));
					witherentity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 200, 4));
					worldIn.addFreshEntity(witherentity);

					for (ServerPlayerEntity serverplayerentity : worldIn.getEntitiesOfClass(ServerPlayerEntity.class,
							witherentity.getBoundingBox().inflate(50.0D))) {
						CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, witherentity);
					}

					for (int k = 0; k < blockpattern.getWidth(); ++k) {
						for (int l = 0; l < blockpattern.getHeight(); ++l) {
							worldIn.updateNeighborsAt(blockpattern$patternhelper.getBlock(k, l, 0).getPos(),
									Blocks.AIR);
						}
					}

				}
			}
		}
	}

	public static BlockPattern getOrCreateIconFull() {
		if (iconPatternFull == null) {
			iconPatternFull = BlockPatternBuilder.start().aisle("!@#$", "%^&*", "()-_", "+=12")
					.where('!', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL1.get())))
					.where('@', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL2.get())))
					.where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL3.get())))
					.where('$', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL4.get())))
					.where('%', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL5.get())))
					.where('^', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL6.get())))
					.where('&', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL7.get())))
					.where('*', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL8.get())))
					.where('(', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL9.get())))
					.where(')', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL10.get())))
					.where('-', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL11.get())))
					.where('_', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL12.get())))
					.where('+', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL13.get())))
					.where('=', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL14.get())))
					.where('1', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL15.get())))
					.where('2', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(DoomBlocks.DOOM_WALL16.get())))
					.build();
		}
		return iconPatternFull;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new IconBlockEntity();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
}