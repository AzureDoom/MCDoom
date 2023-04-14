package mod.azure.doom.block;

import javax.annotation.Nullable;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tileentity.IconBlockEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class DoomWallBlock extends BaseEntityBlock {

	public static final BooleanProperty light = RedstoneTorchBlock.LIT;

	@Nullable
	private static BlockPattern iconPatternFull;

	public DoomWallBlock(Block.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(light, Boolean.valueOf(true)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(light);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return 15;
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		final BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof IconBlockEntity) {
			checkIconSpawn(worldIn, pos, (IconBlockEntity) tileentity);
		}
	}

	public static void checkIconSpawn(Level worldIn, BlockPos pos, IconBlockEntity tileEntityIn) {
		if (!worldIn.isClientSide) {
			final Block block = tileEntityIn.getBlockState().getBlock();
			final boolean flag = block == DoomBlocks.ICON_WALL1.get() || block == DoomBlocks.ICON_WALL2.get() || block == DoomBlocks.ICON_WALL3.get() || block == DoomBlocks.ICON_WALL4.get() || block == DoomBlocks.ICON_WALL5.get() || block == DoomBlocks.ICON_WALL6.get() || block == DoomBlocks.ICON_WALL7.get() || block == DoomBlocks.ICON_WALL8.get() || block == DoomBlocks.ICON_WALL9.get() || block == DoomBlocks.ICON_WALL10.get() || block == DoomBlocks.ICON_WALL11.get()
					|| block == DoomBlocks.ICON_WALL12.get() || block == DoomBlocks.ICON_WALL13.get() || block == DoomBlocks.ICON_WALL14.get() || block == DoomBlocks.ICON_WALL15.get() || block == DoomBlocks.ICON_WALL16.get();
			if (flag && pos.getY() >= 3 && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
				final BlockPattern blockpattern = getOrCreateIconFull();
				final BlockPattern.BlockPatternMatch blockpattern$patternhelper = blockpattern.find(worldIn, pos);
				if (blockpattern$patternhelper != null) {
					for (int i = 0; i < blockpattern.getWidth(); ++i) {
						for (int j = 0; j < blockpattern.getHeight(); ++j) {
							final BlockInWorld cachedblockinfo = blockpattern$patternhelper.getBlock(i, j, 0);
							worldIn.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
							worldIn.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
						}
					}

					final IconofsinEntity witherentity = DoomEntities.ICONOFSIN.get().create(worldIn);
					final BlockPos blockpos = blockpattern$patternhelper.getBlock(1, 2, 0).getPos();
					witherentity.moveTo(blockpos.getX() + 0.5D, blockpos.getY() + 0.55D, blockpos.getZ() + 0.5D, blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
					witherentity.yBodyRot = blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
					witherentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 4));
					witherentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4));
					worldIn.addFreshEntity(witherentity);

					for (final ServerPlayer serverplayerentity : worldIn.getEntitiesOfClass(ServerPlayer.class, witherentity.getBoundingBox().inflate(50.0D))) {
						CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, witherentity);
					}

					for (int k = 0; k < blockpattern.getWidth(); ++k) {
						for (int l = 0; l < blockpattern.getHeight(); ++l) {
							worldIn.updateNeighborsAt(blockpattern$patternhelper.getBlock(k, l, 0).getPos(), Blocks.AIR);
						}
					}

				}
			}
		}
	}

	public static BlockPattern getOrCreateIconFull() {
		if (iconPatternFull == null) {
			iconPatternFull = BlockPatternBuilder.start().aisle("!@#$", "%^&*", "()-_", "+=12").where('!', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL1.get()))).where('@', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL2.get()))).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL3.get()))).where('$', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL4.get())))
					.where('%', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL5.get()))).where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL6.get()))).where('&', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL7.get()))).where('*', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL8.get()))).where('(', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL9.get())))
					.where(')', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL10.get()))).where('-', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL11.get()))).where('_', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL12.get()))).where('+', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL13.get()))).where('=', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL14.get())))
					.where('1', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL15.get()))).where('2', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL16.get()))).build();
		}
		return iconPatternFull;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return DoomEntities.ICON.get().create(pos, state);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}
}