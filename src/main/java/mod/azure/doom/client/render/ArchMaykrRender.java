package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArchMaykrModel;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.renderers.geo.GeoEntityRenderer;

public class ArchMaykrRender extends GeoEntityRenderer<ArchMakyrEntity> {

	public ArchMaykrRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ArchMaykrModel());
	}

	@Override
	public RenderLayer getRenderType(ArchMakyrEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArchMakyrEntity entityLivingBaseIn) {
		return 0.0F;
	}

}