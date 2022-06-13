package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.PossessedSoldierModel;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PossessedSoldierRender extends GeoEntityRenderer<PossessedSoldierEntity> {

	public PossessedSoldierRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PossessedSoldierModel());
	}

	@Override
	public RenderLayer getRenderType(PossessedSoldierEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(PossessedSoldierEntity entityLivingBaseIn) {
		return 0.0F;
	}
}