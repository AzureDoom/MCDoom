package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import mod.azure.doom.client.models.projectiles.BarenBlastModel;
import mod.azure.doom.entities.projectiles.entity.BarenBlastEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class BarenBlastRender extends GeoEntityRenderer<BarenBlastEntity> {

	public BarenBlastRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BarenBlastModel());
	}

	protected int getBlockLightLevel(BarenBlastEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void preRender(PoseStack poseStack, BarenBlastEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

}