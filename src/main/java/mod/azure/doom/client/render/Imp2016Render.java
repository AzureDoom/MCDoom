package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.Imp2016Model;
import mod.azure.doom.entity.tierfodder.Imp2016Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class Imp2016Render extends GeoEntityRenderer<Imp2016Entity> {

	public Imp2016Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new Imp2016Model());
	}

	@Override
	public RenderType getRenderType(Imp2016Entity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Imp2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}