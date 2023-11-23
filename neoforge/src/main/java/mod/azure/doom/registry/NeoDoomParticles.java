package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import mod.azure.doom.particles.PlasmaParticle;
import mod.azure.doom.particles.UnmaykrBoltParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record NeoDoomParticles() {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MCDoom.MOD_ID);

    public static final RegistryObject<SimpleParticleType> PLASMA = PARTICLES.register("plasma", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> PISTOL = PARTICLES.register("pistol", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> UNMAYKR = PARTICLES.register("unmaykr", () -> new SimpleParticleType(true));

    @SubscribeEvent
    public static void registry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PISTOL.get(), PlasmaParticle.Factory::new);
        event.registerSpriteSet(PLASMA.get(), PlasmaParticle.Factory::new);
        event.registerSpriteSet(UNMAYKR.get(), UnmaykrBoltParticle.Factory::new);
    }
}
