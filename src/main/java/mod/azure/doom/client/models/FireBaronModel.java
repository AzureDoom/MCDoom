package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class FireBaronModel extends GeoModel<FireBaronEntity> {

	private static final ResourceLocation[] TEX = { DoomMod.modResource("textures/entity/firebaron.png"), DoomMod.modResource("textures/entity/firebaron_1.png"), DoomMod.modResource("textures/entity/firebaron_2.png") };

	@Override
	public ResourceLocation getModelResource(FireBaronEntity object) {
		return DoomMod.modResource("geo/baron2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FireBaronEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(FireBaronEntity object) {
		return DoomMod.modResource("animations/baron2016.animation.json");
	}

	@Override
	public void setCustomAnimations(FireBaronEntity animatable, long instanceId, AnimationState<FireBaronEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("neck");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(FireBaronEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}