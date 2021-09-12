package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.PossessedScientistModel;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PossessedScientistRender extends GeoEntityRenderer<PossessedScientistEntity> {

	public PossessedScientistRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PossessedScientistModel());
	}

	@Override
	public RenderType getRenderType(PossessedScientistEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(PossessedScientistEntity entityLivingBaseIn) {
		return 0.0F;
	}

}