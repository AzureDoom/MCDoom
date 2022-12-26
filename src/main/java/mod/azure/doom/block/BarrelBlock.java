package mod.azure.doom.block;

import mod.azure.doom.entity.tileentity.BarrelEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BarrelBlock extends Block {
	public static final DirectionProperty direction = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty light = RedstoneTorchBlock.LIT;

	public BarrelBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any().setValue(direction, Direction.NORTH).setValue(light, Boolean.valueOf(true)));
	}

	@Override
	public boolean dropFromExplosion(Explosion explosionIn) {
		return false;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 300;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return true;
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		onCaughtFire(state, worldIn, pos, null, null);
		worldIn.removeBlock(pos, false);
	}

	@Override
	public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isClientSide) {
			BarrelEntity tntentity = new BarrelEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(),
					(double) pos.getZ() + 0.5D, explosionIn.getIndirectSourceEntity());
			worldIn.addFreshEntity(tntentity);
		}
	}

	@Override
	public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
		if (!world.isClientSide) {
			BarrelEntity tntentity = new BarrelEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(),
					(double) pos.getZ() + 0.5D, igniter);
			;
			world.addFreshEntity(tntentity);
		}
	}

	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		if (!worldIn.isClientSide() && !player.isCreative()) {
			onCaughtFire(state, worldIn, pos, null, null);
		}

		super.playerWillDestroy(worldIn, pos, state, player);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		Item item = itemstack.getItem();
		if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
			return super.use(state, worldIn, pos, player, handIn, hit);
		} else {
			onCaughtFire(state, worldIn, pos, hit.getDirection(), player);
			worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
			if (!player.isCreative()) {
				if (item == Items.FLINT_AND_STEEL) {
					itemstack.hurtAndBreak(1, player, (player1) -> {
						player1.broadcastBreakEvent(handIn);
					});
				} else {
					itemstack.shrink(1);
				}
			}

			return InteractionResult.sidedSuccess(worldIn.isClientSide);
		}
	}

	@Override
	public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
		if (!worldIn.isClientSide) {
			Entity entity = projectile.getOwner();
			BlockPos blockpos = hit.getBlockPos();
			onCaughtFire(state, worldIn, blockpos, null, entity instanceof LivingEntity ? (LivingEntity) entity : null);
			worldIn.removeBlock(blockpos, false);
		}

	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return 10;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(direction, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(direction, rot.rotate(state.getValue(direction)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(direction)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(direction, light);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return Shapes.box(0.06f, 0f, 0.06f, 0.94f, 1.0f, 0.94f);
	}

}