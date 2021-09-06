package mod.azure.doom.client.render;

import mod.azure.doom.client.models.WhiplashModel;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class WhiplashRender extends GeoEntityRenderer<WhiplashEntity> {

	public WhiplashRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new WhiplashModel());
	}

	@Override
	public RenderLayer getRenderType(WhiplashEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(WhiplashEntity entityLivingBaseIn) {
		return 0.0F;
	}

}