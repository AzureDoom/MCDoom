package mod.azure.doom.client.render;

import mod.azure.doom.client.models.PossessedWorkerModel;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class PossessedWorkerRender extends GeoEntityRenderer<PossessedScientistEntity> {

	public PossessedWorkerRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new PossessedWorkerModel());
	}

	@Override
	public RenderLayer getRenderType(PossessedScientistEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(PossessedScientistEntity entityLivingBaseIn) {
		return 0.0F;
	}

}