package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.RocketModel;
import mod.azure.doom.entity.projectiles.RocketEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class RocketRender extends GeoProjectilesRenderer<RocketEntity> {

	public RocketRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new RocketModel());
	}

	protected int getBlockLight(RocketEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(RocketEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void renderEarly(RocketEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(animatable.age > 2 ? 0.5F : 0.0F, animatable.age > 2 ? 0.5F : 0.0F,
				animatable.age > 2 ? 0.5F : 0.0F);
	}

}