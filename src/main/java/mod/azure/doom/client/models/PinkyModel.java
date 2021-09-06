package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PinkyModel extends AnimatedGeoModel<PinkyEntity> {

	public PinkyModel() {
	}

	@Override
	public ResourceLocation getModelLocation(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/pinky.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/pinky-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/pinky_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PinkyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
	}
}