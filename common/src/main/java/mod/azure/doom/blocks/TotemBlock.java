package mod.azure.doom.blocks;

import mod.azure.doom.blocks.blockentities.TotemEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class TotemBlock extends BaseEntityBlock implements EntityBlock {

    public static final BooleanProperty light = RedstoneTorchBlock.LIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape X_LENGTH1 = Block.box(5.299999999999999, 0, 5.3000000000000025, 10.7, 15.9, 10.600000000000001);
    private static final VoxelShape X_LENGTH2 = Block.box(-0.20000000000000107, 10.100000000000001, 5.3000000000000025, 16.200000000000003, 15.4, 10.600000000000001);
    private static final VoxelShape Y_LENGTH1 = Block.box(5.3000000000000025, 0, 5.299999999999999, 10.600000000000001, 15.9, 10.7);
    private static final VoxelShape Y_LENGTH2 = Block.box(5.3000000000000025, 10.100000000000001, -0.20000000000000107, 10.600000000000001, 15.4, 16.200000000000003);
    private static final VoxelShape X_AXIS_AABB = Shapes.or(X_LENGTH1, X_LENGTH2);
    private static final VoxelShape Z_AXIS_AABB = Shapes.or(Y_LENGTH1, Y_LENGTH2);

    public TotemBlock() {
        super(Properties.of().sound(SoundType.BONE_BLOCK).noOcclusion().requiresCorrectToolForDrops().explosionResistance(30).strength(4.0F).lightLevel(litBlockEmission(15)));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return lightLevel1 -> BlockStateProperties.MAX_LEVEL_15;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, Services.ENTITIES_HELPER.getTotemEntity(), TotemEntity::tick);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Services.ENTITIES_HELPER.getTotemEntity().create(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING, light);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        var direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? Z_AXIS_AABB : X_AXIS_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}
