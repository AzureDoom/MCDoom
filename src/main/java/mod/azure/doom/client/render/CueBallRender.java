package mod.azure.doom.client.render;

import mod.azure.doom.client.models.CueballModel;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CueBallRender extends GeoEntityRenderer<CueBallEntity> {

	public CueBallRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new CueballModel());
	}

	@Override
	public RenderLayer getRenderType(CueBallEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLight(CueBallEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(CueBallEntity entityLivingBaseIn) {
		return 0.0F;
	}
}