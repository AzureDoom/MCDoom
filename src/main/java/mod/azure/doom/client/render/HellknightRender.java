package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.HellknightModel;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HellknightRender extends GeoEntityRenderer<HellknightEntity> {

	public HellknightRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new HellknightModel());
	}

	@Override
	public RenderType getRenderType(HellknightEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(HellknightEntity entityLivingBaseIn) {
		return 0.0F;
	}

}