package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.CacodemonModel;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CacodemonRender extends GeoEntityRenderer<CacodemonEntity> {

	public CacodemonRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new CacodemonModel());
	}

	@Override
	public RenderType getRenderType(CacodemonEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(CacodemonEntity entityLivingBaseIn) {
		return 0.0F;
	}

}