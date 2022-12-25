package mod.azure.doom.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JumppadBlock extends Block {

	public JumppadBlock() {
		super(Block.Properties.of(Material.HEAVY_METAL).strength(4.0F).sound(SoundType.STONE).noOcclusion());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return Shapes.box(0.00f, 0.0f, 0.00f, 1.0f, 0.2f, 1.0f);
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
		this.jumpEntity(entityIn);
	}

	private void jumpEntity(Entity entity) {
		Vec3 vector3d = entity.getDeltaMovement();
		if (vector3d.y < 0.0D) {
			entity.setDeltaMovement(vector3d.x, 1D, vector3d.z);
		}
	}

	@Override
	public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		double d0 = Math.abs(entityIn.getDeltaMovement().y);
		double d1 = 1.4D + d0 * 0.2D;
		entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, 0.5D));
		super.stepOn(worldIn, pos, state, entityIn);
	}

}