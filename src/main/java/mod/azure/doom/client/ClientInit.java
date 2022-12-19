package mod.azure.doom.client;

import org.lwjgl.glfw.GLFW;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.network.ClientEntityPacket;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.particles.PlasmaParticle;
import mod.azure.doom.util.registry.DoomParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.doom.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.doom.binds");
	public static KeyBinding yeethook = new KeyBinding("key.doom.yeethook", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V,
			"category.doom.binds");

	@Override
	public void onInitializeClient() {
		ModelProviderinit.init();
		DoomRenderRegistry.init();
		HandledScreens.register(DoomMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		ClientPlayNetworking.registerGlobalReceiver(DoomEntityPacket.ID, (client, handler, buf, responseSender) -> {
			ClientEntityPacket.onPacket(client, buf);
		});
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(yeethook);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PLASMA, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PISTOL, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.UNMAYKR, PlasmaParticle.Factory::new);
	}

}