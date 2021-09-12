package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.Baron2016Model;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Baron2016Render extends GeoEntityRenderer<BaronEntity> {

	public Baron2016Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new Baron2016Model());
	}

	@Override
	public RenderType getRenderType(BaronEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(BaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}