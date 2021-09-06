package mod.azure.doom.entity.tileentity;

import java.util.List;
import java.util.Random;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TotemEntity extends BlockEntity implements IAnimatable, Tickable {

	protected final Random random = new Random();
	private final AnimationFactory factory = new AnimationFactory(this);

	private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	public TotemEntity() {
		super(DoomMod.TOTEM);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<TotemEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public void tick() {
		if (this.world.getTime() % 80L == 0L) {
			this.applyEffects();
		}
		World world = this.getWorld();
		if (world != null) {
			BlockPos blockpos = this.getPos();
			if (this.world.isClient) {
				double d0 = (double) blockpos.getX() + 1.0D * (this.random.nextDouble() - 0.25D) * 2.0D;
				double d1 = (double) blockpos.getY() + 1.0D * (this.random.nextDouble() - 0.5D) * 2.0D;
				double d2 = (double) blockpos.getZ() + 1.0D * (this.random.nextDouble() - 0.25D) * 2.0D;
				for (int k = 0; k < 4; ++k) {
					world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0, d1, d2,
							(this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
							(this.random.nextDouble() - 0.5D) * 2.0D);
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