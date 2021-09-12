package mod.azure.doom.client.render.projectiles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.projectiles.EnergyMobModel;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class EnergyCellMobRender extends GeoProjectilesRenderer<EnergyCellMobEntity> {

	public EnergyCellMobRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new EnergyMobModel());
	}

	@Override
	public RenderType getRenderType(EnergyCellMobEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	protected int getBlockLight(EnergyCellEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

}