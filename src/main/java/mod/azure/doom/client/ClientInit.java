package mod.azure.doom.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.particles.PlasmaParticle;
import mod.azure.doom.util.registry.DoomParticles;
import mod.azure.doom.util.registry.ModRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

	public static KeyMapping reload = new KeyMapping("key.doom.reload", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.doom.binds");
	public static KeyMapping yeethook = new KeyMapping("key.doom.yeethook", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.doom.binds");

	@Override
	public void onInitializeClient() {
		ModelProviderinit.init();
		DoomRenderRegistry.init();
		MenuScreens.register(ModRegistry.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(yeethook);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PLASMA, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PISTOL, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.UNMAYKR, PlasmaParticle.Factory::new);
	}

}