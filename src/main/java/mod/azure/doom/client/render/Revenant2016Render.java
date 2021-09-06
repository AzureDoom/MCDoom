package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.Revenant2016Model;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Revenant2016Render extends GeoEntityRenderer<Revenant2016Entity> {

	public Revenant2016Render(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new Revenant2016Model());
	}

	@Override
	public RenderType getRenderType(Revenant2016Entity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Revenant2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}