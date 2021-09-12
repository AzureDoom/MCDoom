package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ShotgunguyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ShotgunguyModel extends AnimatedGeoModel<ShotgunguyEntity> {

	public ShotgunguyModel() {
	}

	@Override
	public ResourceLocation getModelLocation(ShotgunguyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ShotgunguyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/shotgunguy.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ShotgunguyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/shotgunzombie.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ShotgunguyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bipedHead");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}