package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.LostSoulModel;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LostSoulRender extends GeoEntityRenderer<LostSoulEntity> {

	public LostSoulRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new LostSoulModel());
	}

	@Override
	public RenderType getRenderType(LostSoulEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLightLevel(LostSoulEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(LostSoulEntity entityLivingBaseIn) {
		return 0.0F;
	}
}