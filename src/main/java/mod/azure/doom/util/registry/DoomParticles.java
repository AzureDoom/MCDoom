package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoomParticles {

	public static final DefaultParticleType PLASMA = register(new Identifier(DoomMod.MODID, "plasma"), true);
	public static final DefaultParticleType PISTOL = register(new Identifier(DoomMod.MODID, "pistol"), true);
	public static final DefaultParticleType UNMAYKR = register(new Identifier(DoomMod.MODID, "unmaykr"), true);

	private static DefaultParticleType register(Identifier identifier, boolean alwaysSpawn) {
		return Registry.register(Registry.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
