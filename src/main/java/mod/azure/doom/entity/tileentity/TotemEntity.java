package mod.azure.doom.entity.tileentity;

import java.util.List;
import java.util.Random;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TotemEntity extends BlockEntity implements GeoBlockEntity {

	protected final Random random = new Random();
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public TotemEntity(BlockPos pos, BlockState state) {
		super(DoomMod.TOTEM, pos, state);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return PlayState.CONTINUE;
		}));
	}

	public static void tick(Level world, BlockPos pos, BlockState state, TotemEntity blockEntity) {
		if (blockEntity.level.getGameTime() % 80L == 0L) {
			blockEntity.applyEffects();
		}
		if (world != null) {
			if (world.isClientSide()) {
				double d0 = (double) pos.getX() + 1.0D * (blockEntity.random.nextDouble() - 0.25D) * 2.0D;
				double d1 = (double) pos.getY() + 1.0D * (blockEntity.random.nextDouble() - 0.5D) * 2.0D;
				double d2 = (double) pos.getZ() + 1.0D * (blockEntity.random.nextDouble() - 0.25D) * 2.0D;
				for (int k = 0; k < 4; ++k) {
					world.addParticle(DustParticleOptions.REDSTONE, d0, d1, d2,
							(blockEntity.random.nextDouble() - 0.5D) * 2.0D, -blockEntity.random.nextDouble(),
							(blockEntity.random.nextDouble() - 0.5D) * 2.0D);
				}
			}
		}
	}

	@Override
	public void setRemoved() {
		this.removeEffects();
		super.setRemoved();
	}

	private void applyEffects() {
		if (!this.level.isClientSide) {
			AABB axisalignedbb = (new AABB(this.worldPosition)).inflate(40).expandTowards(0.0D,
					(double) this.level.getMaxBuildHeight(), 0.0D);
			List<DemonEntity> list = this.level.getEntitiesOfClass(DemonEntity.class, axisalignedbb);
			for (DemonEntity entity : list) {
				entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
				entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1));
				entity.setGlowingTag(true);
			}
		}
	}

	private void removeEffects() {
		if (!this.level.isClientSide) {
			AABB axisalignedbb = (new AABB(this.worldPosition)).inflate(40).expandTowards(0.0D,
					(double) this.level.getMaxBuildHeight(), 0.0D);
			List<DemonEntity> list = this.level.getEntitiesOfClass(DemonEntity.class, axisalignedbb);
			for (DemonEntity entity : list) {
				entity.setGlowingTag(false);
				entity.removeAllEffects();
			}
		}
	}
}