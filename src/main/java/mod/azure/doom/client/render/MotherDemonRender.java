package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.MotherDemonModel;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MotherDemonRender extends GeoEntityRenderer<MotherDemonEntity> {

	public MotherDemonRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new MotherDemonModel());
	}

	@Override
	public RenderLayer getRenderType(MotherDemonEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(MotherDemonEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void render(GeoModel model, MotherDemonEntity animatable, float partialTicks, RenderLayer type,
			MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		if (animatable.getDataTracker().get(MotherDemonEntity.DEATH_STATE) == 1) {
			model.getBone("Toob").get().setHidden(true);
			model.getBone("heart").get().setHidden(true);
		}
		if (animatable.getDataTracker().get(MotherDemonEntity.DEATH_STATE) == 0) {
			model.getBone("Toob").get().setHidden(false);
			model.getBone("heart").get().setHidden(false);
		}
	}
}