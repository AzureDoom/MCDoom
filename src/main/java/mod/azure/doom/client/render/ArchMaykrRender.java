package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArchMaykrModel;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.geo.render.built.GeoModel;
import software.bernie.geckolib3q.renderers.geo.GeoEntityRenderer;

public class ArchMaykrRender extends GeoEntityRenderer<ArchMakyrEntity> {

	public ArchMaykrRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ArchMaykrModel());
	}

	@Override
	public RenderLayer getRenderType(ArchMakyrEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArchMakyrEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void render(GeoModel model, ArchMakyrEntity animatable, float partialTicks, RenderLayer type,
			MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		if (animatable.getDataTracker().get(ArchMakyrEntity.DEATH_STATE) == 5) {
			model.getBone("rWing4").get().setHidden(true);
			if (animatable.getVariant() == 1) {
				model.getBone("lArm2").get().setHidden(true);
				model.getBone("frontCloak").get().setHidden(true);
				model.getBone("leftCloak").get().setHidden(true);
				model.getBone("rightCloak").get().setHidden(true);
				model.getBone("backCloak").get().setHidden(true);
				model.getBone("lWing2").get().setHidden(true);
			}
			if (animatable.getVariant() == 2) {
				model.getBone("eye").get().setHidden(true);
				model.getBone("lWing1").get().setHidden(true);
				model.getBone("lWing4").get().setHidden(true);
			}
		}
		if (animatable.getDataTracker().get(ArchMakyrEntity.DEATH_STATE) == 4) {
			model.getBone("rWing4").get().setHidden(true);
			if (animatable.getVariant() == 1) {
				model.getBone("lArm2").get().setHidden(true);
				model.getBone("frontCloak").get().setHidden(true);
				model.getBone("leftCloak").get().setHidden(true);
				model.getBone("rightCloak").get().setHidden(true);
				model.getBone("backCloak").get().setHidden(true);
				model.getBone("lWing2").get().setHidden(true);
			}
			if (animatable.getVariant() == 2) {
				model.getBone("eye").get().setHidden(true);
				model.getBone("lWing1").get().setHidden(true);
				model.getBone("lWing4").get().setHidden(true);
			}
		}
		if (animatable.getDataTracker().get(ArchMakyrEntity.DEATH_STATE) == 3) {
			model.getBone("rWing4").get().setHidden(true);
			if (animatable.getVariant() == 1) {
				model.getBone("lArm2").get().setHidden(true);
				model.getBone("frontCloak").get().setHidden(true);
				model.getBone("leftCloak").get().setHidden(true);
				model.getBone("rightCloak").get().setHidden(true);
				model.getBone("backCloak").get().setHidden(true);
			}
			if (animatable.getVariant() == 2) {
				model.getBone("eye").get().setHidden(true);
				model.getBone("lWing4").get().setHidden(true);
			}
		}
		if (animatable.getDataTracker().get(ArchMakyrEntity.DEATH_STATE) == 2) {
			model.getBone("rWing4").get().setHidden(true);
			if (animatable.getVariant() == 2) {
				model.getBone("lWing4").get().setHidden(true);
			}
			if (animatable.getVariant() == 1) {
				model.getBone("lArm2").get().setHidden(true);
			}
		}
		if (animatable.getDataTracker().get(ArchMakyrEntity.DEATH_STATE) == 1) {
			model.getBone("rWing4").get().setHidden(true);
		}
		if (animatable.getDataTracker().get(ArchMakyrEntity.DEATH_STATE) == 0) {
			model.getBone("rWing4").get().setHidden(false);
			if (animatable.getVariant() == 1) {
				model.getBone("frontCloak").get().setHidden(false);
				model.getBone("leftCloak").get().setHidden(false);
				model.getBone("rightCloak").get().setHidden(false);
				model.getBone("backCloak").get().setHidden(false);
				model.getBone("lWing2").get().setHidden(false);
				model.getBone("lArm2").get().setHidden(false);
			}
			if (animatable.getVariant() == 2) {
				model.getBone("eye").get().setHidden(false);
				model.getBone("lWing1").get().setHidden(false);
				model.getBone("lWing4").get().setHidden(false);
			}
		}
	}

}