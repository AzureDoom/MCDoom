package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.Pinky2016Model;
import mod.azure.doom.entity.tierheavy.Pinky2016;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Pinky2016Render extends GeoEntityRenderer<Pinky2016> {

	public Pinky2016Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new Pinky2016Model());
	}

	@Override
	public RenderType getRenderType(Pinky2016 animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Pinky2016 entityLivingBaseIn) {
		return 0.0F;
	}
}