package mod.azure.doom.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.ToIntFunction;

public class JumppadBlock extends Block {

    public JumppadBlock() {
        super(Properties.of().strength(4.0F).sound(SoundType.STONE).lightLevel(litBlockEmission(15)).noOcclusion());
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return lightLevel1 -> BlockStateProperties.MAX_LEVEL_15;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.box(0.00f, 0.0f, 0.00f, 1.0f, 0.2f, 1.0f);
    }

    @Override
    public void updateEntityAfterFallOn(@NotNull BlockGetter worldIn, @NotNull Entity entityIn) {
        this.jumpEntity(entityIn);
    }

    private void jumpEntity(Entity entity) {
        var vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) entity.setDeltaMovement(vector3d.x, 1D, vector3d.z);
    }

    @Override
    public void stepOn(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, Entity entityIn) {
        var d0 = Math.abs(entityIn.getDeltaMovement().y);
        var d1 = 1.4D + d0 * 0.2D;
        entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, 0.5D));
        super.stepOn(worldIn, pos, state, entityIn);
    }

}