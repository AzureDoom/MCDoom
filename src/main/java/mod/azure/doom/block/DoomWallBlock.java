package mod.azure.doom.block;

import javax.annotation.Nullable;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tileentity.IconBlockEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

public class DoomWallBlock extends BaseEntityBlock {

	public static final BooleanProperty light = RedstoneTorchBlock.LIT;

	@Nullable
	private static BlockPattern iconPatternFull;

	public DoomWallBlock() {
		super(FabricBlockSettings.of(Material.METAL).sounds(SoundType.BONE_BLOCK));
		this.registerDefaultState(this.stateDefinition.any().setValue(light, Boolean.valueOf(true)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(light);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return 15;
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof IconBlockEntity) {
			checkIconSpawn(worldIn, pos, (IconBlockEntity) tileentity);
		}
	}

	public static void checkIconSpawn(Level worldIn, BlockPos pos, IconBlockEntity tileEntityIn) {
		if (!worldIn.isClientSide) {
			Block block = tileEntityIn.getBlockState().getBlock();
			boolean flag = block == DoomBlocks.ICON_WALL1 || block == DoomBlocks.ICON_WALL2
					|| block == DoomBlocks.ICON_WALL3 || block == DoomBlocks.ICON_WALL4
					|| block == DoomBlocks.ICON_WALL5 || block == DoomBlocks.ICON_WALL6
					|| block == DoomBlocks.ICON_WALL7 || block == DoomBlocks.ICON_WALL8
					|| block == DoomBlocks.ICON_WALL9 || block == DoomBlocks.ICON_WALL10
					|| block == DoomBlocks.ICON_WALL11 || block == DoomBlocks.ICON_WALL12
					|| block == DoomBlocks.ICON_WALL13 || block == DoomBlocks.ICON_WALL14
					|| block == DoomBlocks.ICON_WALL15 || block == DoomBlocks.ICON_WALL16;
			if (flag && pos.getY() >= 3 && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern blockpattern = getOrCreateIconFull();
				BlockPattern.BlockPatternMatch blockpattern$patternhelper = blockpattern.find(worldIn, pos);
				if (blockpattern$patternhelper != null) {
					for (int i = 0; i < blockpattern.getWidth(); ++i) {
						for (int j = 0; j < blockpattern.getHeight(); ++j) {
							BlockInWorld cachedblockinfo = blockpattern$patternhelper.getBlock(i, j, 0);
							worldIn.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
							worldIn.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
						}
					}

					IconofsinEntity witherentity = DoomEntities.ICONOFSIN.create(worldIn);
					BlockPos blockpos = blockpattern$patternhelper.getBlock(1, 2, 0).getPos();
					witherentity.moveTo((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.55D,
							(double) blockpos.getZ() + 0.5D,
							blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F,
							0.0F);
					witherentity.yBodyRot = blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X
							? 0.0F
							: 90.0F;
					witherentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 4));
					witherentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4));
					worldIn.addFreshEntity(witherentity);

					for (ServerPlayer serverplayerentity : worldIn.getEntitiesOfClass(ServerPlayer.class,
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
					.where('!', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL1)))
					.where('@', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL2)))
					.where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL3)))
					.where('$', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL4)))
					.where('%', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL5)))
					.where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL6)))
					.where('&', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL7)))
					.where('*', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL8)))
					.where('(', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL9)))
					.where(')', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL10)))
					.where('-', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL11)))
					.where('_', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL12)))
					.where('+', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL13)))
					.where('=', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL14)))
					.where('1', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL15)))
					.where('2', BlockInWorld.hasState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL16)))
					.build();
		}
		return iconPatternFull;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return DoomMod.ICON.create(pos, state);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}
}