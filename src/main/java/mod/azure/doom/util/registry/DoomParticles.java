package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class DoomParticles {

	public static final SimpleParticleType PLASMA = register(new ResourceLocation(DoomMod.MODID, "plasma"), true);
	public static final SimpleParticleType PISTOL = register(new ResourceLocation(DoomMod.MODID, "pistol"), true);
	public static final SimpleParticleType UNMAYKR = register(new ResourceLocation(DoomMod.MODID, "unmaykr"), true);

	private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
