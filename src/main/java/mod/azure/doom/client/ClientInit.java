package mod.azure.doom.client;

import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.particles.PlasmaParticle;
import mod.azure.doom.util.registry.DoomParticles;
import mod.azure.doom.util.registry.ModRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModelProviderinit.init();
		DoomRenderRegistry.init();
		MenuScreens.register(ModRegistry.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PLASMA, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PISTOL, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.UNMAYKR, PlasmaParticle.Factory::new);
	}

}