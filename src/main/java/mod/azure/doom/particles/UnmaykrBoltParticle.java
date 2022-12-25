package mod.azure.doom.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class UnmaykrBoltParticle extends TextureSheetParticle {
	private final SpriteSet sprites;

	UnmaykrBoltParticle(ClientLevel p_105546_, double p_105547_, double p_105548_, double p_105549_, double p_105550_,
			SpriteSet p_105551_) {
		super(p_105546_, p_105547_, p_105548_, p_105549_, 0.0D, 0.0D, 0.0D);
		this.sprites = p_105551_;
		this.lifetime = 1;
		this.quadSize = 1.0F - (float) p_105550_ * 0.5F;
		this.setSpriteFromAge(p_105551_);
	}

	public int getLightColor(float p_105562_) {
		return 15728880;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.setSpriteFromAge(this.sprites);
		}
	}

	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_LIT;
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Factory(SpriteSet p_105566_) {
			this.sprites = p_105566_;
		}

		public Particle createParticle(SimpleParticleType p_105577_, ClientLevel p_105578_, double p_105579_,
				double p_105580_, double p_105581_, double p_105582_, double p_105583_, double p_105584_) {
			UnmaykrBoltParticle glowParticle = new UnmaykrBoltParticle(p_105578_, p_105579_, p_105580_, p_105581_,
					p_105582_, this.sprites);
			glowParticle.roll = 180f;
			return new UnmaykrBoltParticle(p_105578_, p_105579_, p_105580_, p_105581_, p_105582_, this.sprites);
		}
	}
}