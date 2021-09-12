package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.EnergyModel;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class EnergyRender extends GeoProjectilesRenderer<EnergyCellEntity> {

	public EnergyRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new EnergyModel());
	}

	protected int getBlockLightLevel(EnergyCellEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderType getRenderType(EnergyCellEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
	
	@Override
	public void renderEarly(EnergyCellEntity animatable, PoseStack stackIn, float ticks,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red,
				green, blue, partialTicks);
		stackIn.scale(animatable.tickCount > 2 ? 1.0F : 0.0F, animatable.tickCount > 2 ? 1.0F : 0.0F,
				animatable.tickCount > 2 ? 1.0F : 0.0F);
	}

}