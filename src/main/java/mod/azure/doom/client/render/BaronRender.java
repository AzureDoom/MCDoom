package mod.azure.doom.client.render;

import mod.azure.doom.client.models.BaronModel;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class BaronRender extends GeoEntityRenderer<BaronEntity> {

	public BaronRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new BaronModel());
	}

	@Override
	public RenderLayer getRenderType(BaronEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(BaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}