package mod.azure.doom.client;

import org.lwjgl.glfw.GLFW;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.client.render.item.GunCraftingItemRender;
import mod.azure.doom.client.render.item.TotemItemRender;
import mod.azure.doom.client.render.projectiles.GrenadeItemRender;
import mod.azure.doom.client.render.weapons.BFG9000Render;
import mod.azure.doom.client.render.weapons.BFGRender;
import mod.azure.doom.client.render.weapons.BallistaRender;
import mod.azure.doom.client.render.weapons.ChaingunRender;
import mod.azure.doom.client.render.weapons.ChainsawRender;
import mod.azure.doom.client.render.weapons.DGaussRender;
import mod.azure.doom.client.render.weapons.DPlamsaRifleRender;
import mod.azure.doom.client.render.weapons.DSGRender;
import mod.azure.doom.client.render.weapons.DarkLordCrucibleRender;
import mod.azure.doom.client.render.weapons.HeavyCannonRender;
import mod.azure.doom.client.render.weapons.PistolRender;
import mod.azure.doom.client.render.weapons.PlasmagunRender;
import mod.azure.doom.client.render.weapons.RocketLauncherRender;
import mod.azure.doom.client.render.weapons.SGRender;
import mod.azure.doom.client.render.weapons.SSGRender;
import mod.azure.doom.client.render.weapons.SentinelHammerRender;
import mod.azure.doom.client.render.weapons.SwordCrucibleRender;
import mod.azure.doom.client.render.weapons.UnmaykrRender;
import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.network.EntityPacketOnClient;
import mod.azure.doom.particles.PlasmaParticle;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.doom.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.doom.binds");

	@SuppressWarnings("unchecked")
	@Override
	public void onInitializeClient() {
		ModelProviderinit.init();
		DoomRenderRegistry.init();
		ScreenRegistry.register(DoomMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		GeoItemRenderer.registerItemRenderer(DoomBlocks.TOTEM.asItem(), new TotemItemRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.GUN_TABLE, new GunCraftingItemRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.BFG, new BFG9000Render());
		GeoItemRenderer.registerItemRenderer(DoomItems.BFG_ETERNAL, new BFGRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.SG, new SGRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.SSG, new SSGRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.CHAINGUN, new ChaingunRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.BALLISTA, new BallistaRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.PLASMAGUN, new PlasmagunRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.HEAVYCANNON, new HeavyCannonRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.CHAINSAW_ETERNAL, new ChainsawRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.PISTOL, new PistolRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.ROCKETLAUNCHER, new RocketLauncherRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.UNMAYKR, new UnmaykrRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.SENTINELHAMMER, new SentinelHammerRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.CRUCIBLESWORD, new SwordCrucibleRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.DARKLORDCRUCIBLE, new DarkLordCrucibleRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.DSG, new DSGRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.DPLASMARIFLE, new DPlamsaRifleRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.DGAUSS, new DGaussRender());
		GeoItemRenderer.registerItemRenderer(DoomItems.GRENADE, new GrenadeItemRender());
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
			EntityPacketOnClient.onPacket(ctx, buf);
		});
		KeyBindingHelper.registerKeyBinding(reload);
		TrinketRendererRegistry.registerRenderer(DoomItems.SOULCUBE,
				(stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance,
						tickDelta, animationProgress, headYaw, headPitch) -> {
					if (entity instanceof AbstractClientPlayerEntity player) {
						TrinketRenderer.translateToChest(matrices,
								(PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player);
						matrices.scale(0.35F, 0.35F, 0.35F);
						matrices.multiply(Direction.DOWN.getRotationQuaternion());
						MinecraftClient.getInstance().getItemRenderer().renderItem(stack, Mode.NONE, light,
								OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
					}
				});
		requestParticleTexture(new Identifier("doom:particles/plasma"));
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PLASMA, PlasmaParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(DoomParticles.PISTOL, PlasmaParticle.Factory::new);
	}

	public static void requestParticleTexture(Identifier id) {
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
				.register(((texture, registry) -> registry.register(id)));
	}

}