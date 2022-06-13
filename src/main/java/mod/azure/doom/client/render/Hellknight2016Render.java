package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.Hellknight2016Model;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Hellknight2016Render extends GeoEntityRenderer<Hellknight2016Entity> {

	public Hellknight2016Render(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new Hellknight2016Model());
	}

	@Override
	public RenderLayer getRenderType(Hellknight2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}