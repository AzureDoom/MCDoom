package mod.azure.doom.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class E1M1TurnableHurtBlock extends Block {

	public static final DirectionProperty direction = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty light = RedstoneTorchBlock.LIT;

	public E1M1TurnableHurtBlock() {
		super(FabricBlockSettings.of(Material.METAL).sounds(SoundType.BONE_BLOCK));
		this.registerDefaultState(this.stateDefinition.any().setValue(direction, Direction.NORTH).setValue(light, Boolean.valueOf(true)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(direction, context.getHorizontalDirection());
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
	public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
		return 15;
	}

	@Override
	public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		if (!entityIn.fireImmune() && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn))
			entityIn.hurt(entityIn.damageSources().hotFloor(), 1.0F);
		super.stepOn(worldIn, pos, state, entityIn);
	}

}