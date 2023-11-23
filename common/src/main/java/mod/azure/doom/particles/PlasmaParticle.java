package mod.azure.doom.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

import java.util.Random;

public class PlasmaParticle extends TextureSheetParticle {
    static final Random RANDOM = new Random();
    private final SpriteSet sprites;

    PlasmaParticle(ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteSet) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        friction = 0.96F;
        speedUpWhenYMotionIsBlocked = true;
        sprites = spriteSet;
        quadSize *= 0.75F;
        hasPhysics = false;
        setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float color) {
        var f = (age + color) / lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        final var i = super.getLightColor(color);
        var j = i & 255;
        final var k = i >> 16 & 255;
        j += (int) (f * 15.0F * 16.0F);
        if (j > 240) j = 240;

        return j | k << 16;
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(sprites);
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites1;

        public Factory(SpriteSet sprites) {
            sprites1 = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            final var glowParticle = new PlasmaParticle(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D, sprites1);
            glowParticle.setColor(1.0F, 0.9F, 1.0F);
            glowParticle.setParticleSpeed(g * 0.25D, h * 0.25D, i * 0.25D);
            glowParticle.setLifetime(clientWorld.random.nextInt(2) + 2);
            return glowParticle;
        }
    }
}