package mod.azure.doom.client.render.projectiles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.ArchvileFiringModel;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ArchvileFiringRender extends GeoProjectilesRenderer<DoomFireEntity> {

	public ArchvileFiringRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArchvileFiringModel());
	}

	protected int getBlockLightLevel(DoomFireEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderType getRenderType(DoomFireEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

}