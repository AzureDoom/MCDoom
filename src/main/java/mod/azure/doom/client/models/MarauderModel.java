package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MarauderModel extends GeoModel<MarauderEntity> {

	@Override
	public ResourceLocation getModelResource(MarauderEntity object) {
		return DoomMod.modResource("geo/marauder.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MarauderEntity object) {
		return DoomMod.modResource("textures/entity/marauder.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MarauderEntity object) {
		return DoomMod.modResource("animations/marauder.animation.json");
	}

	@Override
	public void setCustomAnimations(MarauderEntity animatable, long instanceId, AnimationState<MarauderEntity> animationState) {
		var head = getAnimationProcessor().getBone("bone");

		if (head != null) 
			head.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);
		
		super.setCustomAnimations(animatable, instanceId, animationState);
	}

	@Override
	public RenderType getRenderType(MarauderEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}