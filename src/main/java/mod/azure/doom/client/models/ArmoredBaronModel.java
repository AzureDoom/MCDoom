package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ArmoredBaronModel extends GeoModel<ArmoredBaronEntity> {

	@Override
	public ResourceLocation getModelResource(ArmoredBaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ArmoredBaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/armoredbaron.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ArmoredBaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/baron2016.animation.json");
	}

	@Override
	public void setCustomAnimations(ArmoredBaronEntity animatable, long instanceId, AnimationState<ArmoredBaronEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("neck");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 360F));
		}
	}

	@Override
	public RenderType getRenderType(ArmoredBaronEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}