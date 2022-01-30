package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.particles.PlasmaParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoomMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoomParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(ForgeRegistries.PARTICLE_TYPES, DoomMod.MODID);

	public static final RegistryObject<SimpleParticleType> PLASMA = PARTICLES.register("plasma",
			() -> new SimpleParticleType(true));

	public static final RegistryObject<SimpleParticleType> PISTOL = PARTICLES.register("pistol",
			() -> new SimpleParticleType(true));

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void registry(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(PISTOL.get(), PlasmaParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(PLASMA.get(), PlasmaParticle.Factory::new);
	}

}
