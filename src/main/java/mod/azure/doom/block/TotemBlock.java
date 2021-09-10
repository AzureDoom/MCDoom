package mod.azure.doom.block;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TotemBlock extends BlockWithEntity implements BlockEntityProvider {

	public static final DirectionProperty FACING = Properties.FACING;
	private static final VoxelShape X_LENGTH1 = Block.createCuboidShape(5.299999999999999, 0, 5.3000000000000025, 10.7, 15.9,
			10.600000000000001);
	private static final VoxelShape X_LENGTH2 = Block.createCuboidShape(-0.20000000000000107, 10.100000000000001, 5.3000000000000025,
			16.200000000000003, 15.4, 10.600000000000001);
	private static final VoxelShape Y_LENGTH1 = Block.createCuboidShape(5.3000000000000025, 0, 5.299999999999999, 10.600000000000001,
			15.9, 10.7);
	private static final VoxelShape Y_LENGTH2 = Block.createCuboidShape(5.3000000000000025, 10.100000000000001, -0.20000000000000107,
			10.600000000000001, 15.4, 16.200000000000003);
	private static final VoxelShape X_AXIS_AABB = VoxelShapes.union(X_LENGTH1, X_LENGTH2);
	private static final VoxelShape Z_AXIS_AABB = VoxelShapes.union(Y_LENGTH1, Y_LENGTH2);
	protected Random RANDOM = new Random();

	public TotemBlock() {
		super(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 3).nonOpaque()
				.requiresTool().strength(3, 3).luminance(15));
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TotemEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return checkType(type, DoomMod.TOTEM, TotemEntity::tick);
	}

	@Override
	protected void dropExperience(ServerWorld world, BlockPos pos, int size) {
		super.dropExperience(world, pos, MathHelper.nextInt(RANDOM, 3, 7));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 15F;
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = (Direction) state.get(FACING);
		return direction.getAxis() == Direction.Axis.X ? Z_AXIS_AABB : X_AXIS_AABB;
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return this.getDefaultState().with(FACING, context.getPlayerLookDirection());
	}
}
