package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.client.models.projectiles.BarenBlastModel;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderer.geo.GeoProjectilesRenderer;

public class BarenBlastRender extends GeoProjectilesRenderer<BarenBlastEntity> {

	public BarenBlastRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new BarenBlastModel());
	}

	protected int getBlockLight(BarenBlastEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(BarenBlastEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}