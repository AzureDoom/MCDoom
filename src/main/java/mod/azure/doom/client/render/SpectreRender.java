package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SpectreModel;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class SpectreRender extends GeoEntityRenderer<SpectreEntity> {

	public SpectreRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new SpectreModel());
	}

	@Override
	public RenderLayer getRenderType(SpectreEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void render(GeoModel model, SpectreEntity animatable, float partialTicks, RenderLayer type,
			MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		// TODO Auto-generated method stub
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, 0.1F);
	}

	@Override
	protected float getDeathMaxRotation(SpectreEntity entityLivingBaseIn) {
		return 0.0F;
	}
}