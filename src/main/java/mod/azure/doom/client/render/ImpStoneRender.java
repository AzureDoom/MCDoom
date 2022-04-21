package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ImpStoneModel;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.renderers.geo.GeoEntityRenderer;

public class ImpStoneRender extends GeoEntityRenderer<ImpStoneEntity> {

	public ImpStoneRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ImpStoneModel());
	}

	@Override
	public RenderLayer getRenderType(ImpStoneEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ImpStoneEntity entityLivingBaseIn) {
		return 0.0F;
	}

}