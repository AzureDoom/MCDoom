package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PinkyModel extends AnimatedTickingGeoModel<PinkyEntity> {

	@Override
	public ResourceLocation getModelResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"geo/" + (object.getVariant() == 3 ? "pinky2016" : "pinky") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 1 ? "pinky-texturemap"
				: object.getVariant() == 3 ? "pinky2016" : "pinky_green") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "pinky2016." : "pinky_") + "animation.json");
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