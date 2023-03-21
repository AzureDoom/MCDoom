package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MechaZombieModel extends GeoModel<MechaZombieEntity> {

	@Override
	public ResourceLocation getModelResource(MechaZombieEntity object) {
		return DoomMod.modResource("geo/mechazombie.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MechaZombieEntity object) {
		return DoomMod.modResource("textures/entity/mechazombie.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MechaZombieEntity object) {
		return DoomMod.modResource("animations/mechazombie_animation.json");
	}

	@Override
	public void setCustomAnimations(MechaZombieEntity animatable, long instanceId, AnimationState<MechaZombieEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("head");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(MechaZombieEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}