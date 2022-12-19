package mod.azure.doom.client.render;

import mod.azure.doom.client.models.DoomHunterModel;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DoomHunterRender extends GeoEntityRenderer<DoomHunterEntity> {

	public DoomHunterRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new DoomHunterModel());
	}

	@Override
	protected float getDeathMaxRotation(DoomHunterEntity entityLivingBaseIn) {
		return 0.0F;
	}
	
	@Override
	public void preRender(MatrixStack poseStack, DoomHunterEntity animatable, BakedGeoModel model,
			VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay,
				red, green, blue, alpha);
		if (animatable.getDataTracker().get(DoomHunterEntity.DEATH_STATE) == 0) 
			model.getBone("sled").get().setHidden(false);
		if (animatable.getDataTracker().get(DoomHunterEntity.DEATH_STATE) == 1) 
			model.getBone("sled").get().setHidden(true);
		if (animatable.getHealth() < 0.01 || animatable.isDead())
			model.getBone("sled").get().setHidden(true);
	}

}