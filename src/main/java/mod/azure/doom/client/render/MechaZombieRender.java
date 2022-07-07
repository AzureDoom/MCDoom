package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.MechaZombieModel;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MechaZombieRender extends GeoEntityRenderer<MechaZombieEntity> {

	public MechaZombieRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new MechaZombieModel());
	}

	@Override
	public RenderLayer getRenderType(MechaZombieEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(MechaZombieEntity entityLivingBaseIn) {
		return 0.0F;
	}

}