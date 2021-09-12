package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.PainModel;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PainRender extends GeoEntityRenderer<PainEntity> {

	public PainRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PainModel());
	}

	@Override
	public RenderType getRenderType(PainEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLightLevel(PainEntity entityIn, BlockPos partialTicks) {
		return entityIn.getAttckingState() == 1 ? 15 : 1;
	}

	@Override
	protected float getDeathMaxRotation(PainEntity entityLivingBaseIn) {
		return 0.0F;
	}

}