package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArchvileEternalModel;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class ArchvileEternalRender extends GeoEntityRenderer<ArchvileEntity> {

	public ArchvileEternalRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new ArchvileEternalModel());
	}

	@Override
	public RenderLayer getRenderType(ArchvileEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLight(ArchvileEntity entity, BlockPos blockPos) {
		return entity.getAttckingState() == 1 ? 15 : 1;
	}

	protected float getLyingAngle(ArchvileEntity entityLivingBaseIn) {
		return 0.0F;
	}

}