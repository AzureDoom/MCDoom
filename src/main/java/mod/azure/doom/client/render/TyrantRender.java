package mod.azure.doom.client.render;

import mod.azure.doom.client.models.TyrantModel;
import mod.azure.doom.entity.tiersuperheavy.Cyberdemon2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class TyrantRender extends GeoEntityRenderer<Cyberdemon2016Entity> {

	public TyrantRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new TyrantModel());
	}

	@Override
	public RenderLayer getRenderType(Cyberdemon2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Cyberdemon2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}