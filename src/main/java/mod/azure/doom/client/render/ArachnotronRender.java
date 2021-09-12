package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.ArachnotronModel;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ArachnotronRender extends GeoEntityRenderer<ArachnotronEntity> {

	public ArachnotronRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArachnotronModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderType getRenderType(ArachnotronEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArachnotronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}