package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.PinkyModel;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PinkyRender extends GeoEntityRenderer<PinkyEntity> {

	public PinkyRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PinkyModel());
	}

	@Override
	public RenderLayer getRenderType(PinkyEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(PinkyEntity entityLivingBaseIn) {
		return 0.0F;
	}

}