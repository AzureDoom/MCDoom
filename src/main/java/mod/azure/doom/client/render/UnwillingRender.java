package mod.azure.doom.client.render;

import mod.azure.doom.client.models.UnwillingModel;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class UnwillingRender extends GeoEntityRenderer<UnwillingEntity> {

	public UnwillingRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new UnwillingModel());
	}

	@Override
	public RenderLayer getRenderType(UnwillingEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(UnwillingEntity entityLivingBaseIn) {
		return 0.0F;
	}

}