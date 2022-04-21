package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SpiderMastermind2016Model;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.renderers.geo.GeoEntityRenderer;

public class SpiderMastermind2016Render extends GeoEntityRenderer<SpiderMastermind2016Entity> {

	public SpiderMastermind2016Render(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SpiderMastermind2016Model());
	}

	@Override
	public RenderLayer getRenderType(SpiderMastermind2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(SpiderMastermind2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}