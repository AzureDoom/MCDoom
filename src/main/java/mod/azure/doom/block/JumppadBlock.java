package mod.azure.doom.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class JumppadBlock extends Block {

	public JumppadBlock() {
		super(AbstractBlock.Settings.of(Material.METAL).strength(4.0F).sounds(BlockSoundGroup.METAL).nonOpaque());
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0.00f, 0.0f, 0.00f, 1.0f, 0.2f, 1.0f);
	}
	
	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.0F, DamageSource.FALL);
	}

	@Override
	public void onEntityLand(BlockView world, Entity entity) {
		this.jumpEntity(entity);
	}

	private void jumpEntity(Entity entity) {
		Vec3d vector3d = entity.getVelocity();
		if (vector3d.y < 0.0D) {
			entity.setVelocity(vector3d.x, 1D, vector3d.z);
		}
	}
	
	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		double d0 = Math.abs(entity.getVelocity().y);
		double d1 = 1.4D + d0 * 0.2D;
		entity.setVelocity(entity.getVelocity().multiply(d1, 1.0D, 0.5D));
		super.onSteppedOn(world, pos, state, entity);
	}
}