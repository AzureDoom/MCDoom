package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.doom.client.models.weapons.DSGModel;
import mod.azure.doom.item.weapons.DShotgun;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3q.renderers.geo.GeoItemRenderer;

public class DSGRender extends GeoItemRenderer<DShotgun> {
	public DSGRender() {
		super(new DSGModel());
	}

	@Override
	public void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStackIn,
			VertexConsumerProvider bufferIn, int combinedLightIn, int combinedOverlayIn) {
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
	public Integer getUniqueID(DShotgun animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getUniqueID(animatable);
	}
}