package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.MotherDemonModel;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MotherDemonRender extends GeoEntityRenderer<MotherDemonEntity> {

	public MotherDemonRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MotherDemonModel());
	}

	@Override
	protected float getDeathMaxRotation(MotherDemonEntity entityLivingBaseIn) {
		return 0.0F;
	}
	
	@Override
	public void preRender(PoseStack poseStack, MotherDemonEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay,
				red, green, blue, alpha);
		if (animatable.getEntityData().get(MotherDemonEntity.DEATH_STATE) == 1) {
			model.getBone("Toob").get().setHidden(true);
			model.getBone("heart").get().setHidden(true);
		}
		if (animatable.getEntityData().get(MotherDemonEntity.DEATH_STATE) == 0) {
			model.getBone("Toob").get().setHidden(false);
			model.getBone("heart").get().setHidden(false);
		}
	}
}