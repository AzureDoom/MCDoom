package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.lighting.DiffuseLighting;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.doom.client.models.weapons.BallistaModel;
import mod.azure.doom.item.weapons.Ballista;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BallistaRender extends GeoItemRenderer<Ballista> {
	public BallistaRender() {
		super(new BallistaModel());
	}

	@Override
	public void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStackIn,
			VertexConsumerProvider bufferIn, int combinedLightIn, int combinedOverlayIn) {
		currentTransform = mode;
		if (mode == ModelTransformation.Mode.GUI) {
			matrixStackIn.push();
			VertexConsumerProvider.Immediate irendertypebuffer$impl = MinecraftClient.getInstance().getBufferBuilders()
					.getEntityVertexConsumers();
			DiffuseLighting.setupFlatGuiLighting();
			super.render(itemStack, mode, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			irendertypebuffer$impl.draw();
			RenderSystem.enableDepthTest();
			DiffuseLighting.setup3DGuiLighting();
			matrixStackIn.pop();
		} else {
			super.render(itemStack, mode, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		}
	}

	private ModelTransformation.Mode currentTransform;

	@Override
	public Integer getUniqueID(Ballista animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getUniqueID(animatable);
	}
}