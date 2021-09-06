package mod.azure.doom.client.render.projectiles.entity;

import mod.azure.doom.client.models.projectiles.BloodBoltModel;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoProjectilesRenderer;

public class BloodBoltRender extends GeoProjectilesRenderer<BloodBoltEntity> {

	public BloodBoltRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new BloodBoltModel());
	}

	@Override
	public RenderLayer getRenderType(BloodBoltEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}
}