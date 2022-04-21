package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArachnotronModel;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.renderers.geo.GeoEntityRenderer;

public class ArachonotronRender extends GeoEntityRenderer<ArachnotronEntity> {

	public ArachonotronRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ArachnotronModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderLayer getRenderType(ArachnotronEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArachnotronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}