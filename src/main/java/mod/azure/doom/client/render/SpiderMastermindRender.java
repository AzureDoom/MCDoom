package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.SpiderMastermindModel;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SpiderMastermindRender extends GeoEntityRenderer<SpiderMastermindEntity> {

	public SpiderMastermindRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new SpiderMastermindModel());
	}

	@Override
	public RenderType getRenderType(SpiderMastermindEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(SpiderMastermindEntity entityLivingBaseIn) {
		return 0.0F;
	}

}