package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArmoredBaronModel;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class ArmoredBaronRender extends GeoEntityRenderer<ArmoredBaronEntity> {

	public ArmoredBaronRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new ArmoredBaronModel());
	}

	@Override
	public RenderLayer getRenderType(ArmoredBaronEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArmoredBaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}