package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.GladiatorModel;
import mod.azure.doom.entity.tiersuperheavy.GladiatorEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GladiatorRender extends GeoEntityRenderer<GladiatorEntity> {

	public GladiatorRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GladiatorModel());
	}

	@Override
	public RenderType getRenderType(GladiatorEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(GladiatorEntity entityLivingBaseIn) {
		return 0.0F;
	}

}