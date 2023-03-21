package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ProwlerModel extends GeoModel<ProwlerEntity> {

	@Override
	public ResourceLocation getModelResource(ProwlerEntity object) {
		return DoomMod.modResource("geo/prowler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ProwlerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 2 ? "prowler_cursed" : "prowler") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(ProwlerEntity object) {
		return DoomMod.modResource("animations/imp2016.animation.json");
	}

	@Override
	public void setCustomAnimations(ProwlerEntity animatable, long instanceId, AnimationState<ProwlerEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("neck");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(ProwlerEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}