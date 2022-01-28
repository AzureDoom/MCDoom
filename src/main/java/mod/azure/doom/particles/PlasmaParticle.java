package mod.azure.doom.particles;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(value = EnvType.CLIENT)
public class PlasmaParticle extends SpriteBillboardParticle {
	static final Random RANDOM = new Random();
	private final SpriteProvider spriteProvider;

	PlasmaParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY,
			double velocityZ, SpriteProvider spriteProvider) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.velocityMultiplier = 0.96f;
		this.field_28787 = true;
		this.spriteProvider = spriteProvider;
		this.scale *= 0.75f;
		this.collidesWithWorld = false;
		this.setSpriteForAge(spriteProvider);
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public int getBrightness(float tint) {
		float f = ((float) this.age + tint) / (float) this.maxAge;
		f = MathHelper.clamp(f, 0.0f, 1.0f);
		int i = super.getBrightness(tint);
		int j = i & 0xFF;
		int k = i >> 16 & 0xFF;
		if ((j += (int) (f * 15.0f * 16.0f)) > 240) {
			j = 240;
		}
		return j | k << 16;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteForAge(this.spriteProvider);
	}

	@Environment(value = EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d,
				double e, double f, double g, double h, double i) {
			double rotate = MathHelper.clamp(f, 0.0f, 360.0f);
			PlasmaParticle glowParticle = new PlasmaParticle(clientWorld, d, e, f, 0.0, 0.0, 0.0, this.spriteProvider);
			glowParticle.setColor(1.0f, 0.9f, 1.0f);
			glowParticle.setVelocity(g * 0.25, h * 0.25, i * 0.25);
			glowParticle.angle = (float) rotate;
			glowParticle.setMaxAge(clientWorld.random.nextInt(2) + 2);
			return glowParticle;
		}
	}
}