package mod.azure.doom.platform;

import mod.azure.doom.platform.services.DoomParticlesHelper;
import mod.azure.doom.registry.FabricDoomParticles;
import net.minecraft.core.particles.SimpleParticleType;

public class FabricDoomParticlesHelper implements DoomParticlesHelper {
    @Override
    public SimpleParticleType getPLASMA() {
        return FabricDoomParticles.PLASMA;
    }

    @Override
    public SimpleParticleType getPISTOL() {
        return FabricDoomParticles.PISTOL;
    }

    @Override
    public SimpleParticleType getUNMAYKR() {
        return FabricDoomParticles.UNMAYKR;
    }
}
