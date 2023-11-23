package mod.azure.doom.platform;

import mod.azure.doom.platform.services.DoomParticlesHelper;
import mod.azure.doom.registry.NeoDoomParticles;
import net.minecraft.core.particles.SimpleParticleType;

public class NeoForgeDoomParticlesHelper implements DoomParticlesHelper {
    @Override
    public SimpleParticleType getPLASMA() {
        return NeoDoomParticles.PLASMA.get();
    }

    @Override
    public SimpleParticleType getPISTOL() {
        return NeoDoomParticles.PISTOL.get();
    }

    @Override
    public SimpleParticleType getUNMAYKR() {
        return NeoDoomParticles.UNMAYKR.get();
    }
}
