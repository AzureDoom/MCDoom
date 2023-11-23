package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public record FabricDoomParticles() {
    public static final SimpleParticleType PLASMA = register(MCDoom.modResource("plasma"), true);
    public static final SimpleParticleType PISTOL = register(MCDoom.modResource("pistol"), true);
    public static final SimpleParticleType UNMAYKR = register(MCDoom.modResource("unmaykr"), true);

    private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
    }
}
