package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.BloodMaykrModel;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BloodMaykrRender extends GeoEntityRenderer<BloodMaykrEntity> {

	public BloodMaykrRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new BloodMaykrModel());
	}

	@Override
	public RenderLayer getRenderType(BloodMaykrEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(BloodMaykrEntity entityLivingBaseIn) {
		return 0.0F;
	}

}