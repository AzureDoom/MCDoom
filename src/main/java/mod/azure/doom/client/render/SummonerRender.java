package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SummonerModel;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SummonerRender extends GeoEntityRenderer<SummonerEntity> {

	public SummonerRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SummonerModel());
	}

	@Override
	public RenderLayer getRenderType(SummonerEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(SummonerEntity entityLivingBaseIn) {
		return 0.0F;
	}

}