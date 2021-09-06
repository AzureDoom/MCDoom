package mod.azure.doom.client;

import org.lwjgl.glfw.GLFW;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.gui.GunTableScreen;
import mod.azure.doom.client.render.item.GunCraftingItemRender;
import mod.azure.doom.client.render.item.TotemItemRender;
import mod.azure.doom.client.render.weapons.BFG9000Render;
import mod.azure.doom.client.render.weapons.BFGRender;
import mod.azure.doom.client.render.weapons.BallistaRender;
import mod.azure.doom.client.render.weapons.ChaingunRender;
import mod.azure.doom.client.render.weapons.ChainsawRender;
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
import mod.azure.doom.util.packets.EntityPacket;
import mod.azure.doom.util.packets.EntityPacketOnClient;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import nerdhub.cardinal.components.api.event.ItemComponentCallbackV2;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;
import top.theillusivec4.curios.api.CuriosComponent;
import top.theillusivec4.curios.api.type.component.IRenderableCurio;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.doom.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.doom.binds");

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
		ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
			EntityPacketOnClient.onPacket(ctx, buf);
		});
		KeyBindingHelper.registerKeyBinding(reload);
		ItemComponentCallbackV2.event(DoomItems.SOULCUBE).register(((item, itemStack,
				componentContainer) -> componentContainer.put(CuriosComponent.ITEM_RENDER, new IRenderableCurio() {
					@Override
					public void render(String identifier, int index, MatrixStack matrixStack,
							VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity livingEntity,
							float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
							float netHeadYaw, float headPitch) {
						IRenderableCurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
						IRenderableCurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
						matrixStack.scale(0.35F, 0.35F, 0.35F);
						matrixStack.translate(0.0F, 0.5F, -0.4F);
						matrixStack.multiply(Direction.DOWN.getRotationQuaternion());
						MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, Mode.NONE, light,
								OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider);
					}
				})));
	}

}