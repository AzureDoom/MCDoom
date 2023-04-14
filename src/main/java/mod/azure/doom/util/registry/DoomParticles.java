package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class DoomParticles {

	public static final SimpleParticleType PLASMA = register(DoomMod.modResource("plasma"), true);
	public static final SimpleParticleType PISTOL = register(DoomMod.modResource("pistol"), true);
	public static final SimpleParticleType UNMAYKR = register(DoomMod.modResource("unmaykr"), true);

	private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
		return Registry.register(Registry.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
