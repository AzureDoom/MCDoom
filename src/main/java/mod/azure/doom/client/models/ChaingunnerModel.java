package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChaingunnerModel extends GeoModel<ChaingunnerEntity> {

	public ChaingunnerModel() {
	}

	@Override
	public ResourceLocation getModelResource(ChaingunnerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChaingunnerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/chaingunner.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ChaingunnerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/chaingunner.animation.json");
	}

	@Override
	public void setCustomAnimations(ChaingunnerEntity animatable, long instanceId, AnimationState<ChaingunnerEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("head");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(ChaingunnerEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}