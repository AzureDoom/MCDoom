package mod.azure.doom.client.render;

import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.GoreNestModel;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GoreNestRender extends GeoEntityRenderer<GoreNestEntity> {

	public GoreNestRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new GoreNestModel());
	}

	@Override
	public RenderLayer getRenderType(GoreNestEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(GoreNestEntity entityLivingBaseIn) {
		return 0.0F;
	}
}