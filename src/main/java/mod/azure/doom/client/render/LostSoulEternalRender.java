package mod.azure.doom.client.render;

import mod.azure.doom.client.models.LostSoulEternalModel;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3q.renderers.geo.GeoEntityRenderer;

public class LostSoulEternalRender extends GeoEntityRenderer<LostSoulEntity> {

	public LostSoulEternalRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new LostSoulEternalModel());
	}

	@Override
	public RenderLayer getRenderType(LostSoulEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLight(LostSoulEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(LostSoulEntity entityLivingBaseIn) {
		return 0.0F;
	}
}