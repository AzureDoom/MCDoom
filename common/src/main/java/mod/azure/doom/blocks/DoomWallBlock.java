package mod.azure.doom.blocks;

import mod.azure.doom.blocks.blockentities.IconBlockEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class DoomWallBlock extends BaseEntityBlock {

    public static final BooleanProperty light = RedstoneTorchBlock.LIT;

    public static final IntegerProperty LIGHT_LEVEL = BlockStateProperties.AGE_15;

    @Nullable
    private static BlockPattern iconPatternFull;

    public DoomWallBlock() {
        super(Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.METAL).lightLevel(litBlockEmission(15)));
        this.registerDefaultState(this.stateDefinition.any().setValue(light, Boolean.valueOf(true)));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return lightLevel1 -> BlockStateProperties.MAX_LEVEL_15;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(light);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        var tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof IconBlockEntity iconBlockEntity) checkIconSpawn(worldIn, pos, iconBlockEntity);
    }

    public static void checkIconSpawn(Level worldIn, BlockPos pos, IconBlockEntity tileEntityIn) {
        if (!worldIn.isClientSide) {
            var block = tileEntityIn.getBlockState().getBlock();
            var flag = block == Services.BLOCKS_HELPER.getWall1() || block == Services.BLOCKS_HELPER.getWall2() || block == Services.BLOCKS_HELPER.getWall3() || block == Services.BLOCKS_HELPER.getWall4() || block == Services.BLOCKS_HELPER.getWall5() || block == Services.BLOCKS_HELPER.getWall6() || block == Services.BLOCKS_HELPER.getWall7() || block == Services.BLOCKS_HELPER.getWall8() || block == Services.BLOCKS_HELPER.getWall9() || block == Services.BLOCKS_HELPER.getWall10() || block == Services.BLOCKS_HELPER.getWall11() || block == Services.BLOCKS_HELPER.getWall12() || block == Services.BLOCKS_HELPER.getWall13() || block == Services.BLOCKS_HELPER.getWall14() || block == Services.BLOCKS_HELPER.getWall15() || block == Services.BLOCKS_HELPER.getWall16();
            if (flag && pos.getY() >= 3 && worldIn.getDifficulty() != Difficulty.PEACEFUL) {
                var blockpattern = getOrCreateIconFull();
                var patternHelper = blockpattern.find(worldIn, pos);
                if (patternHelper != null) {
                    for (var i = 0; i < blockpattern.getWidth(); ++i)
                        for (var j = 0; j < blockpattern.getHeight(); ++j) {
                            var cachedblockinfo = patternHelper.getBlock(i, j, 0);
                            worldIn.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
                            worldIn.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
                        }

                    var witherentity = Services.ENTITIES_HELPER.getIconofSinEntity().create(worldIn);
                    var blockpos = patternHelper.getBlock(1, 2, 0).getPos();
                    witherentity.moveTo(blockpos.getX() + 0.5D, blockpos.getY() + 0.55D, blockpos.getZ() + 0.5D, patternHelper.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
                    witherentity.yBodyRot = patternHelper.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
                    witherentity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 4));
                    witherentity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4));
                    worldIn.addFreshEntity(witherentity);

                    for (ServerPlayer serverplayerentity : worldIn.getEntitiesOfClass(ServerPlayer.class, witherentity.getBoundingBox().inflate(50.0D)))
                        CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, witherentity);

                    for (var k = 0; k < blockpattern.getWidth(); ++k)
                        for (var l = 0; l < blockpattern.getHeight(); ++l)
                            worldIn.updateNeighborsAt(patternHelper.getBlock(k, l, 0).getPos(), Blocks.AIR);
                }
            }
        }
    }

    public static BlockPattern getOrCreateIconFull() {
        if (iconPatternFull == null) {
            iconPatternFull = BlockPatternBuilder.start().aisle("!@#$", "%^&*", "()-_", "+=12").where('!', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall1()))).where('@', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall2()))).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall3()))).where('$', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall4()))).where('%', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall5()))).where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall6()))).where('&', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall7()))).where('*', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall8()))).where('(', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall9()))).where(')', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall10()))).where('-', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall11()))).where('_', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall12()))).where('+', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall13()))).where('=', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall14()))).where('1', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall15()))).where('2', BlockInWorld.hasState(BlockStatePredicate.forBlock(Services.BLOCKS_HELPER.getWall16()))).build();
        }
        return iconPatternFull;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return Services.ENTITIES_HELPER.getIconBlockEntity().create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}