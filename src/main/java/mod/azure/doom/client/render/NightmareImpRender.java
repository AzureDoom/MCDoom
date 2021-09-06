package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ImpNightmareModel;
import mod.azure.doom.entity.tierfodder.NightmareImpEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NightmareImpRender extends GeoEntityRenderer<NightmareImpEntity> {

	public NightmareImpRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ImpNightmareModel());
	}

	@Override
	public RenderLayer getRenderType(NightmareImpEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(NightmareImpEntity entityLivingBaseIn) {
		return 0.0F;
	}

}