package mod.azure.doom.client.render.projectiles.entity;

import mod.azure.doom.client.models.projectiles.EnergyMobModel;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderer.geo.GeoProjectilesRenderer;

public class EnergyCellMobRender extends GeoProjectilesRenderer<EnergyCellMobEntity> {

	public EnergyCellMobRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new EnergyMobModel());
	}
	
	protected int getBlockLight(EnergyCellEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(EnergyCellMobEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}