package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class IconofsinModel extends GeoModel<IconofsinEntity> {

	@Override
	public ResourceLocation getModelResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/icon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/iconofsin.png");
	}

	@Override
	public ResourceLocation getAnimationResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/icon.animation.json");
	}

	@Override
	public void setCustomAnimations(IconofsinEntity animatable, long instanceId, AnimationState<IconofsinEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("head");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() - 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 360F));
		}
	}

	@Override
	public RenderType getRenderType(IconofsinEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}