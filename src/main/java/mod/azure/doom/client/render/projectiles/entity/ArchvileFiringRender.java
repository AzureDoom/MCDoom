package mod.azure.doom.client.render.projectiles.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.ArchvileFiringModel;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ArchvileFiringRender extends GeoProjectilesRenderer<DoomFireEntity> {

	public ArchvileFiringRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ArchvileFiringModel());
	}

	protected int getBlockLight(DoomFireEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(DoomFireEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}