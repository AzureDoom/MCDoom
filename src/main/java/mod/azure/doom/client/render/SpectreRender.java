package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SpectreModel;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpectreRender extends GeoEntityRenderer<SpectreEntity> {

	public SpectreRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SpectreModel());
	}

	@Override
	protected float getDeathMaxRotation(SpectreEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void actuallyRender(MatrixStack poseStack, SpectreEntity animatable, BakedGeoModel model,
			RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender,
			float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
				packedLight, packedOverlay, red, green, blue, 0.1F);
	}
}