package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.RevenantModel;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RevenantRender extends GeoEntityRenderer<RevenantEntity> {

	public RevenantRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new RevenantModel());
	}

	@Override
	public RenderType getRenderType(RevenantEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(RevenantEntity entityLivingBaseIn) {
		return 0.0F;
	}

}