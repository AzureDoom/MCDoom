package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.Revenant2016Model;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Revenant2016Render extends GeoEntityRenderer<Revenant2016Entity> {

	public Revenant2016Render(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new Revenant2016Model());
	}

	@Override
	public RenderLayer getRenderType(Revenant2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Revenant2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}