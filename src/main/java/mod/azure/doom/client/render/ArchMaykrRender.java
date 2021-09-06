package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.ArchMaykrModel;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ArchMaykrRender extends GeoEntityRenderer<ArchMakyrEntity> {

	public ArchMaykrRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ArchMaykrModel());
	}

	@Override
	public RenderType getRenderType(ArchMakyrEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(ArchMakyrEntity entityLivingBaseIn) {
		return 0.0F;
	}

}