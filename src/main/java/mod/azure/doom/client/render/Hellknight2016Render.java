package mod.azure.doom.client.render;

import mod.azure.doom.client.models.Hellknight2016Model;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class Hellknight2016Render extends GeoEntityRenderer<Hellknight2016Entity> {

	public Hellknight2016Render(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new Hellknight2016Model());
	}

	@Override
	public RenderLayer getRenderType(Hellknight2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}