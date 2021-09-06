package mod.azure.doom.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class JumppadBlock extends Block {

	public JumppadBlock() {
		super(Block.Properties.of(Material.HEAVY_METAL).strength(4.0F).sound(SoundType.STONE).noOcclusion());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.box(0.00f, 0.0f, 0.00f, 1.0f, 0.2f, 1.0f);
	}

	@Override
	public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		entityIn.causeFallDamage(5.0F, 0.0F);
	}

	@Override
	public void updateEntityAfterFallOn(IBlockReader worldIn, Entity entityIn) {
		this.jumpEntity(entityIn);
	}

	private void jumpEntity(Entity entity) {
		Vector3d vector3d = entity.getDeltaMovement();
		if (vector3d.y < 0.0D) {
			entity.setDeltaMovement(vector3d.x, 1D, vector3d.z);
		}
	}

	@Override
	public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
		double d0 = Math.abs(entityIn.getDeltaMovement().y);
		double d1 = 1.4D + d0 * 0.2D;
		entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, 0.5D));
		super.stepOn(worldIn, pos, entityIn);
	}

}