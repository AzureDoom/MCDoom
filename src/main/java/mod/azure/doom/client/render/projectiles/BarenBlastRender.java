package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.BarenBlastModel;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BarenBlastRender extends GeoProjectilesRenderer<BarenBlastEntity> {

	public BarenBlastRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BarenBlastModel());
	}

	protected int getBlockLightLevel(BarenBlastEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderType getRenderType(BarenBlastEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

}