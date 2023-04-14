package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TurretEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TurretModel extends GeoModel<TurretEntity> {

	@Override
	public ResourceLocation getModelResource(TurretEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/turret.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TurretEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/turret.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TurretEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/turret.animation.json");
	}

	@Override
	public void setCustomAnimations(TurretEntity animatable, long instanceId, AnimationState<TurretEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("eye");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 360F));
		}
	}

	@Override
	public RenderType getRenderType(TurretEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}