package mod.azure.doom.entity.tileentity;

import java.util.List;
import java.util.Random;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
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
	
	public static void tick(World world, BlockPos pos, BlockState state, TotemEntity blockEntity) {
		if (blockEntity.world.getTime() % 80L == 0L) {
			blockEntity.applyEffects();
		}
		if (world != null) {
			if (world.isClient) {
				double d0 = (double) pos.getX() + 1.0D * (blockEntity.random.nextDouble() - 0.25D) * 2.0D;
				double d1 = (double) pos.getY() + 1.0D * (blockEntity.random.nextDouble() - 0.5D) * 2.0D;
				double d2 = (double) pos.getZ() + 1.0D * (blockEntity.random.nextDouble() - 0.25D) * 2.0D;
				for (int k = 0; k < 4; ++k) {
					world.addParticle(new DustParticleEffect(DustParticleEffect.RED, 1), d0, d1, d2,
							(blockEntity.random.nextDouble() - 0.5D) * 2.0D, -blockEntity.random.nextDouble(),
							(blockEntity.random.nextDouble() - 0.5D) * 2.0D);
				}
			}
		}
	}

	@Override
	public void markRemoved() {
		this.removeEffects();
		super.markRemoved();
	}

	private void applyEffects() {
		if (!this.world.isClient()) {
			Box axisalignedbb = (new Box(this.pos)).expand(40).stretch(0.0D, (double) this.world.getHeight(), 0.0D);
			List<DemonEntity> list = this.world.getNonSpectatingEntities(DemonEntity.class, axisalignedbb);
			for (DemonEntity entity : list) {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1000, 1));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1000, 1));
				entity.setGlowing(true);
			}
		}
	}

	private void removeEffects() {
		if (!this.world.isClient()) {
			Box axisalignedbb = (new Box(this.pos)).expand(40).stretch(0.0D, (double) this.world.getHeight(), 0.0D);
			List<DemonEntity> list = this.world.getNonSpectatingEntities(DemonEntity.class, axisalignedbb);
			for (DemonEntity entity : list) {
				entity.setGlowing(false);
				entity.clearStatusEffects();
			}
		}
	}
}