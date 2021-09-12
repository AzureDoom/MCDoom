package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.GoreNestModel;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GoreNestRender extends GeoEntityRenderer<GoreNestEntity> {

	public GoreNestRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GoreNestModel());
	}

	@Override
	public RenderType getRenderType(GoreNestEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(GoreNestEntity entityLivingBaseIn) {
		return 0.0F;
	}
}