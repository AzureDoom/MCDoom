package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

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