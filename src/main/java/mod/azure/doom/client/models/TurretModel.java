package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TurretEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
	public void setCustomAnimations(TurretEntity animatable, long instanceId,
			AnimationState<TurretEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("eye");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

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