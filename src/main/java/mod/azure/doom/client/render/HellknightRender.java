package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.HellknightModel;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HellknightRender extends GeoEntityRenderer<HellknightEntity> {

	public HellknightRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new HellknightModel());
	}

	@Override
	public RenderLayer getRenderType(HellknightEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(HellknightEntity entityLivingBaseIn) {
		return 0.0F;
	}

}