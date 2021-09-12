package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.SpectreModel;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SpectreRender extends GeoEntityRenderer<SpectreEntity> {

	public SpectreRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new SpectreModel());
	}

	@Override
	public RenderType getRenderType(SpectreEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(SpectreEntity entityLivingBaseIn) {
		return 0.0F;
	}
	
	@Override
	public void render(GeoModel model, SpectreEntity animatable, float partialTicks, RenderType type,
			PoseStack matrixStackIn, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		// TODO Auto-generated method stub
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn,
				packedOverlayIn, red, green, blue, 0.1F);
	}
}