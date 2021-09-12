package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.SpiderMastermind2016Model;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SpiderMastermind2016Render extends GeoEntityRenderer<SpiderMastermind2016Entity> {

	public SpiderMastermind2016Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new SpiderMastermind2016Model());
	}

	@Override
	public RenderType getRenderType(SpiderMastermind2016Entity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(SpiderMastermind2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}