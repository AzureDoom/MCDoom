package mod.azure.doom.particles;

import java.util.Random;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class PlasmaParticle extends TextureSheetParticle {
	static final Random RANDOM = new Random();
	private final SpriteSet sprites;

	PlasmaParticle(ClientLevel p_172136_, double p_172137_, double p_172138_, double p_172139_, double p_172140_, double p_172141_, double p_172142_, SpriteSet p_172143_) {
		super(p_172136_, p_172137_, p_172138_, p_172139_, p_172140_, p_172141_, p_172142_);
		friction = 0.96F;
		speedUpWhenYMotionIsBlocked = true;
		sprites = p_172143_;
		quadSize *= 0.75F;
		hasPhysics = false;
		setSpriteFromAge(p_172143_);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public int getLightColor(float p_172146_) {
		var f = (age + p_172146_) / lifetime;
		f = Mth.clamp(f, 0.0F, 1.0F);
		final var i = super.getLightColor(p_172146_);
		var j = i & 255;
		final var k = i >> 16 & 255;
		j += (int) (f * 15.0F * 16.0F);
		if (j > 240)
			j = 240;

		return j | k << 16;
	}

	@Override
	public void tick() {
		super.tick();
		setSpriteFromAge(sprites);
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public Factory(SpriteSet p_172151_) {
			sprite = p_172151_;
		}

		@Override
		public Particle createParticle(SimpleParticleType p_172162_, ClientLevel p_172163_, double p_172164_, double p_172165_, double p_172166_, double p_172167_, double p_172168_, double p_172169_) {
			final var glowparticle = new PlasmaParticle(p_172163_, p_172164_, p_172165_, p_172166_, 0.0D, 0.0D, 0.0D, sprite);
			glowparticle.setColor(1.0F, 0.9F, 1.0F);
			glowparticle.setParticleSpeed(p_172167_ * 0.25D, p_172168_ * 0.25D, p_172169_ * 0.25D);
			glowparticle.setLifetime(p_172163_.random.nextInt(2) + 2);
			return glowparticle;
		}
	}
}