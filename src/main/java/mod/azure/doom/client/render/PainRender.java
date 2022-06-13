package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.PainModel;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PainRender extends GeoEntityRenderer<PainEntity> {

	public PainRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PainModel());
	}

	@Override
	public RenderLayer getRenderType(PainEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLight(PainEntity entity, BlockPos blockPos) {
		return entity.getAttckingState() == 1 ? 15 : 1;
	}

	@Override
	protected float getDeathMaxRotation(PainEntity entityLivingBaseIn) {
		return 0.0F;
	}

}