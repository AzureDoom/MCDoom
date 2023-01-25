package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;

public class PinkyModel extends GeoModel<PinkyEntity> {

	@Override
	public ResourceLocation getModelResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/" + (object.getVariant() == 3 ? "pinky2016" : "pinky") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 4 ? "pinky_green"
				: object.getVariant() == 2 ? "pinky-64"
						:object.getVariant() == 3 ? "pinky2016" : "pinky-texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "pinky2016." : "pinky_") + "animation.json");
	}

	@Override
	public void setCustomAnimations(PinkyEntity animatable, long instanceId,
			AnimationState<PinkyEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + (animatable.getVariant() == 3 ? 90 : 30)) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 500F));
		}
	}

	@Override
	public RenderType getRenderType(PinkyEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}