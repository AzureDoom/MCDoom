package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PossessedSoldierModel extends GeoModel<PossessedSoldierEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_1.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_2.png") };

	@Override
	public ResourceLocation getModelResource(PossessedSoldierEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/" + (object.getVariant() == 3 ? "possessedsoldier-shield" : object.getVariant() == 2 ? "possessedsoldier-eternal" : "possessedsoldier") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PossessedSoldierEntity object) {
		return object.getVariant() == 2 && object.isAggressive() ? TEX[object.getFlameTimer()] : object.getVariant() == 2 && !object.isAggressive() ? new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-eternal.png") : object.getVariant() == 3 ? new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-shield.png") : new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PossessedSoldierEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessedsoldier.animation.json");
	}

	@Override
	public void setCustomAnimations(PossessedSoldierEntity animatable, long instanceId, AnimationState<PossessedSoldierEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("head");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(PossessedSoldierEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}