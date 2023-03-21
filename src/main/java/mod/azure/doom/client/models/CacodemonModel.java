package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CacodemonModel extends GeoModel<CacodemonEntity> {

	@Override
	public ResourceLocation getModelResource(CacodemonEntity object) {
		return DoomMod.modResource("geo/" + (object.getVariant() == 1 ? "cacodemon64" : object.getVariant() >= 3 ? "cacodemoneternal" : "cacodemon") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CacodemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 1 ? "cacodemon64" : object.getVariant() == 3 ? "cacodemoneternal" : object.getVariant() == 4 ? "cacodemon2016" : "cacodemon") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(CacodemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/" + (object.getVariant() == 3 ? "cacodemoneternal." : "cacodemon_") + "animation.json");
	}

	@Override
	public void setCustomAnimations(CacodemonEntity animatable, long instanceId, AnimationState<CacodemonEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("body");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(CacodemonEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}