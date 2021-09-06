package mod.azure.doom.client.render;

import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.GargoyleModel;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GargoyleRender extends GeoEntityRenderer<GargoyleEntity> {

	public GargoyleRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new GargoyleModel());
	}

	@Override
	public RenderLayer getRenderType(GargoyleEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(GargoyleEntity entityLivingBaseIn) {
		return 0.0F;
	}

}