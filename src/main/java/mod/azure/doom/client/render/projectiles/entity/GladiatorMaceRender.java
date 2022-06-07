package mod.azure.doom.client.render.projectiles.entity;

import mod.azure.doom.client.models.projectiles.GladiatorMaceModel;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class GladiatorMaceRender extends GeoProjectilesRenderer<GladiatorMaceEntity> {

	public GladiatorMaceRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new GladiatorMaceModel());
	}

	protected int getBlockLightLevel(GladiatorMaceEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(GladiatorMaceEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}