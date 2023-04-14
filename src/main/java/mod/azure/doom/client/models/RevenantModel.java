package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RevenantModel extends GeoModel<RevenantEntity> {

	@Override
	public ResourceLocation getModelResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_nojetpack.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@Override
	public void setCustomAnimations(RevenantEntity animatable, long instanceId, AnimationState<RevenantEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("head");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(RevenantEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}