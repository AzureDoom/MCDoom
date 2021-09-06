package mod.azure.doom.block;

import java.util.Iterator;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tileentity.IconBlockEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class DoomWallBlock extends BlockWithEntity {

	public static final BooleanProperty light = RedstoneTorchBlock.LIT;
	private static BlockPattern iconPatternFull;

	public DoomWallBlock() {
		super(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.BONE));
		this.setDefaultState(this.stateManager.getDefaultState().with(light, Boolean.valueOf(true)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(light);
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 15;
	}

	@Override
	public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.onPlaced(worldIn, pos, state, placer, stack);
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof IconBlockEntity) {
			checkIconSpawn(worldIn, pos, (IconBlockEntity) tileentity);
		}
	}

	public static void checkIconSpawn(World world, BlockPos pos, IconBlockEntity blockEntity) {
		if (!world.isClient()) {
			BlockState block = blockEntity.getCachedState();
			boolean flag = block.isOf(DoomBlocks.ICON_WALL1) || block.isOf(DoomBlocks.ICON_WALL2)
					|| block.isOf(DoomBlocks.ICON_WALL3) || block.isOf(DoomBlocks.ICON_WALL4)
					|| block.isOf(DoomBlocks.ICON_WALL5) || block.isOf(DoomBlocks.ICON_WALL6)
					|| block.isOf(DoomBlocks.ICON_WALL7) || block.isOf(DoomBlocks.ICON_WALL8)
					|| block.isOf(DoomBlocks.ICON_WALL9) || block.isOf(DoomBlocks.ICON_WALL10)
					|| block.isOf(DoomBlocks.ICON_WALL11) || block.isOf(DoomBlocks.ICON_WALL12)
					|| block.isOf(DoomBlocks.ICON_WALL13) || block.isOf(DoomBlocks.ICON_WALL14)
					|| block.isOf(DoomBlocks.ICON_WALL15) || block.isOf(DoomBlocks.ICON_WALL16);
			if (flag && pos.getY() >= 3 && world.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern blockPattern = getOrCreateIconFull();
				BlockPattern.Result result = blockPattern.searchAround(world, pos);
				if (result != null) {
					for (int i = 0; i < blockPattern.getWidth(); ++i) {
						for (int j = 0; j < blockPattern.getHeight(); ++j) {
							CachedBlockPosition cachedBlockPosition = result.translate(i, j, 0);
							world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
							world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(),
									Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
						}
					}

					IconofsinEntity witherentity = ModEntityTypes.ICONOFSIN.create(world);
					BlockPos blockPos = result.translate(1, 2, 0).getBlockPos();
					witherentity.refreshPositionAndAngles((double) blockPos.getX() + 0.5D,
							(double) blockPos.getY() + 0.55D, (double) blockPos.getZ() + 0.5D,
							result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
					witherentity.bodyYaw = result.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
					witherentity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 4));
					witherentity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 4));
					world.spawnEntity(witherentity);

					Iterator<ServerPlayerEntity> var13 = world.getNonSpectatingEntities(ServerPlayerEntity.class,
							witherentity.getBoundingBox().expand(50.0D)).iterator();
					while (var13.hasNext()) {
						ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var13.next();
						Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, witherentity);
					}

					for (int k = 0; k < blockPattern.getWidth(); ++k) {
						for (int l = 0; l < blockPattern.getHeight(); ++l) {
							world.updateNeighbors(result.translate(k, l, 0).getBlockPos(), Blocks.AIR);
						}
					}

				}
			}
		}
	}

	public static BlockPattern getOrCreateIconFull() {
		if (iconPatternFull == null) {
			iconPatternFull = BlockPatternBuilder.start().aisle("!@#$", "%^&*", "()-_", "+=12")
					.where('!',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL1)))
					.where('@',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL2)))
					.where('#',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL3)))
					.where('$',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL4)))
					.where('%',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL5)))
					.where('^',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL6)))
					.where('&',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL7)))
					.where('*',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL8)))
					.where('(',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL9)))
					.where(')',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL10)))
					.where('-',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL11)))
					.where('_',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL12)))
					.where('+',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL13)))
					.where('=',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL14)))
					.where('1',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL15)))
					.where('2',
							CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(DoomBlocks.ICON_WALL16)))
					.build();
		}
		return iconPatternFull;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new IconBlockEntity(pos, state);
	}
}