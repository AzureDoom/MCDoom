package mod.azure.doom.client.render;

import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;
import mod.azure.doom.client.models.Imp2016Model;
import mod.azure.doom.entity.tierfodder.Imp2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class Imp2016Render extends GeoEntityRenderer<Imp2016Entity> {

	public Imp2016Render(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new Imp2016Model());
	}

	@Override
	public RenderLayer getRenderType(Imp2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Imp2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}