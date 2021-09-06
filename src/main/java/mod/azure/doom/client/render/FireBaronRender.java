package mod.azure.doom.client.render;

import mod.azure.doom.client.models.FireBaronModel;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class FireBaronRender extends GeoEntityRenderer<FireBaronEntity> {

	public FireBaronRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new FireBaronModel());
	}

	@Override
	public RenderLayer getRenderType(FireBaronEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(FireBaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}