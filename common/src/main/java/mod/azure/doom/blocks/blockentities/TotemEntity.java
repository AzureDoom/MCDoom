package mod.azure.doom.blocks.blockentities;

import mod.azure.azurelib.animatable.GeoBlockEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class TotemEntity extends BlockEntity implements GeoBlockEntity {

    protected final Random random = new Random();
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public TotemEntity(BlockPos pos, BlockState state) {
        super(Services.ENTITIES_HELPER.getTotemEntity(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, TotemEntity blockEntity) {
        if (blockEntity.level != null && blockEntity.level.getGameTime() % 80L == 0L)
            blockEntity.applyEffects();
        if (world != null && world.isClientSide()) {
            final double x = pos.getX() + 1.0D * (blockEntity.random.nextDouble() - 0.25D) * 2.0D;
            final double y = pos.getY() + 1.0D * (blockEntity.random.nextDouble() - 0.5D) * 2.0D;
            final double z = pos.getZ() + 1.0D * (blockEntity.random.nextDouble() - 0.25D) * 2.0D;
            for (var k = 0; k < 4; ++k)
                world.addParticle(DustParticleOptions.REDSTONE, x, y, z,
                        (blockEntity.random.nextDouble() - 0.5D) * 2.0D, -blockEntity.random.nextDouble(),
                        (blockEntity.random.nextDouble() - 0.5D) * 2.0D);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, event -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    public void setRemoved() {
        removeEffects();
        super.setRemoved();
    }

    private void applyEffects() {
        assert level != null;
        if (!level.isClientSide) {
            final var axisalignedbb = new AABB(worldPosition).inflate(40).expandTowards(0.0D, level.getMaxBuildHeight(),
                    0.0D);
            final var list = level.getEntitiesOfClass(DemonEntity.class, axisalignedbb);
            for (final var entity : list) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1));
                entity.setGlowingTag(true);
            }
        }
    }

    private void removeEffects() {
        assert level != null;
        if (!level.isClientSide) {
            final var axisalignedbb = new AABB(worldPosition).inflate(40).expandTowards(0.0D, level.getMaxBuildHeight(),
                    0.0D);
            final var list = level.getEntitiesOfClass(DemonEntity.class, axisalignedbb);
            for (final var entity : list) {
                entity.setGlowingTag(false);
                entity.removeAllEffects();
            }
        }
    }
}