package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MotherDemonModel;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class MotherDemonRender extends GeoEntityRenderer<MotherDemonEntity> {

	public MotherDemonRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new MotherDemonModel());
	}

	@Override
	public RenderLayer getRenderType(MotherDemonEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	protected float getLyingAngle(MotherDemonEntity entityLivingBaseIn) {
		return 0.0F;
	}

}