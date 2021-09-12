package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.ImpModel;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ImpRender extends GeoEntityRenderer<ImpEntity> {

	public ImpRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ImpModel());
	}

	@Override
	public RenderType getRenderType(ImpEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ImpEntity entityLivingBaseIn) {
		return 0.0F;
	}

}