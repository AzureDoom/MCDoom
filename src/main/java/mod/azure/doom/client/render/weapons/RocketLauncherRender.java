package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.doom.client.models.weapons.RocketLauncherModel;
import mod.azure.doom.item.weapons.RocketLauncher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3q.renderers.geo.GeoItemRenderer;

public class RocketLauncherRender extends GeoItemRenderer<RocketLauncher> {
	public RocketLauncherRender() {
		super(new RocketLauncherModel());
	}

	@Override
	public void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn,
			int combinedLightIn, int combinedOverlayIn) {
		currentTransform = mode;
		if (mode == ModelTransformation.Mode.GUI) {
			matrixStackIn.push();
			VertexConsumerProvider.Immediate irendertypebuffer$impl = MinecraftClient.getInstance().getBufferBuilders()
					.getEntityVertexConsumers();
			DiffuseLighting.disableGuiDepthLighting();
			super.render(itemStack, mode, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			irendertypebuffer$impl.draw();
			RenderSystem.enableDepthTest();
			DiffuseLighting.enableGuiDepthLighting();
			matrixStackIn.pop();
		} else {
			super.render(itemStack, mode, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		}
	}

	private ModelTransformation.Mode currentTransform;

	@Override
	public Integer getUniqueID(RocketLauncher animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getUniqueID(animatable);
	}
}