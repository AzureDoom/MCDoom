package mod.azure.doom.client.models;

import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PinkyModel extends GeoModel<PinkyEntity> {

	@Override
	public ResourceLocation getModelResource(PinkyEntity object) {
		return DoomMod.modResource("geo/" + (object.getVariant() == 3 ? "pinky2016" : "pinky") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PinkyEntity object) {
		return DoomMod.modResource("textures/entity/" + (object.getVariant() == 4 ? "pinky_green" : object.getVariant() == 2 ? "pinky-64" : object.getVariant() == 3 ? "pinky2016" : "pinky-texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/" + (object.getVariant() == 3 ? "pinky2016." : "pinky_") + "animation.json");
	}

	@Override
	public void setCustomAnimations(PinkyEntity animatable, long instanceId, AnimationState<PinkyEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);
	}

	@Override
	public RenderType getRenderType(PinkyEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}