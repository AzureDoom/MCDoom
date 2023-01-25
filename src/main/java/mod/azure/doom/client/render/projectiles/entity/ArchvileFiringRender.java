package mod.azure.doom.client.render.projectiles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.ArchvileFiringModel;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;

public class ArchvileFiringRender extends GeoEntityRenderer<DoomFireEntity> {

	public ArchvileFiringRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArchvileFiringModel());
	}

	protected int getBlockLightLevel(DoomFireEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void preRender(PoseStack poseStack, DoomFireEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F,
				animatable.tickCount > 2 ? 0.5F : 0.0F);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
				packedOverlay, red, green, blue, alpha);
	}

}