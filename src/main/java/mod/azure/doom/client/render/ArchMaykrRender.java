package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.ArchMaykrModel;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ArchMaykrRender extends GeoEntityRenderer<ArchMakyrEntity> {

	public ArchMaykrRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArchMaykrModel());
	}

	@Override
	public RenderType getRenderType(ArchMakyrEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArchMakyrEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void render(GeoModel model, ArchMakyrEntity animatable, float partialTicks, RenderType type,
			PoseStack matrixStackIn, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 5) {
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
		if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 4) {
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
		if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 3) {
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
		if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 2) {
			model.getBone("rWing4").get().setHidden(true);
			if (animatable.getVariant() == 2) {
				model.getBone("lWing4").get().setHidden(true);
			}
			if (animatable.getVariant() == 1) {
				model.getBone("lArm2").get().setHidden(true);
			}
		}
		if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 1) {
			model.getBone("rWing4").get().setHidden(true);
		}
		if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 0) {
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