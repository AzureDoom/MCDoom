package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.ArchvileModel;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ArchvileRender extends GeoEntityRenderer<ArchvileEntity> {

	public ArchvileRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArchvileModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	public RenderType getRenderType(ArchvileEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected int getBlockLightLevel(ArchvileEntity entityIn, BlockPos partialTicks) {
		return entityIn.getAttckingState() == 1 ? 15 : 1;
	}
	
	@Override
	protected float getDeathMaxRotation(ArchvileEntity entityLivingBaseIn) {
		return 0;
	}

}