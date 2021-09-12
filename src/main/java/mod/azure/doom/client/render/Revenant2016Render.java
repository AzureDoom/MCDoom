package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.Revenant2016Model;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Revenant2016Render extends GeoEntityRenderer<Revenant2016Entity> {

	public Revenant2016Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new Revenant2016Model());
	}

	@Override
	public RenderType getRenderType(Revenant2016Entity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Revenant2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}