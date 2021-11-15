package mod.azure.doom.client.render;

import mod.azure.doom.client.models.RevenantModel;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RevenantRender extends GeoEntityRenderer<RevenantEntity> {

	public RevenantRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new RevenantModel());
	}

	@Override
	public RenderLayer getRenderType(RevenantEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(RevenantEntity entityLivingBaseIn) {
		return 0.0F;
	}

}