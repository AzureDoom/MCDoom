package mod.azure.doom.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class UnmaykrBoltParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    UnmaykrBoltParticle(ClientLevel clientWorld, double x, double y, double z, double velocityX, SpriteSet spriteSet) {
        super(clientWorld, x, y, z, 0.0D, 0.0D, 0.0D);
        sprites = spriteSet;
        lifetime = 1;
        quadSize = 1.0F - (float) velocityX * 0.5F;
        setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float color) {
        return 15728880;
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (age++ >= lifetime) remove();
        else setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites1;

        public Factory(SpriteSet sprites) {
            sprites1 = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            final var glowParticle = new UnmaykrBoltParticle(clientWorld, d, e, f, g, sprites1);
            glowParticle.roll = 180f;
            return new UnmaykrBoltParticle(clientWorld, d, e, f, g, sprites1);
        }
    }
}