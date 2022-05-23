package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.DoomHunterModel;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DoomHunterRender extends GeoEntityRenderer<DoomHunterEntity> {

	public DoomHunterRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new DoomHunterModel());
	}

	@Override
	public RenderType getRenderType(DoomHunterEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(DoomHunterEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void render(GeoModel model, DoomHunterEntity animatable, float partialTicks, RenderType type,
			PoseStack matrixStackIn, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		if (animatable.getEntityData().get(DoomHunterEntity.DEATH_STATE) == 0) {
			model.getBone("sled").get().setHidden(false);
		}
		if (animatable.getEntityData().get(DoomHunterEntity.DEATH_STATE) == 1) {
			model.getBone("sled").get().setHidden(true);
		}
	}

}