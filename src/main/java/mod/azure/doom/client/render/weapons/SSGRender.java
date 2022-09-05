package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.doom.client.models.weapons.SSGModel;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.util.PlayerProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3q.geo.render.built.GeoBone;
import software.bernie.geckolib3q.renderers.geo.GeoItemRenderer;

public class SSGRender extends GeoItemRenderer<SuperShotgun> {

	public SSGRender() {
		super(new SSGModel());
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
	public Integer getUniqueID(SuperShotgun animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getUniqueID(animatable);
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("hook")) {
			bone.setHidden(
					!((PlayerProperties) MinecraftClient.getInstance().cameraEntity).hasMeatHook() ? false : true);
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}