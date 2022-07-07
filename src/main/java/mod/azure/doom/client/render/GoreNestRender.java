package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.GoreNestModel;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GoreNestRender extends GeoEntityRenderer<GoreNestEntity> {

	public GoreNestRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new GoreNestModel());
	}

	@Override
	public RenderLayer getRenderType(GoreNestEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(GoreNestEntity entityLivingBaseIn) {
		return 0.0F;
	}
}