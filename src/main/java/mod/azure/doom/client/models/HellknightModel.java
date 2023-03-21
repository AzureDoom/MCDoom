package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class HellknightModel extends GeoModel<HellknightEntity> {

	@Override
	public ResourceLocation getModelResource(HellknightEntity object) {
		return DoomMod.modResource("geo/hellknight.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/hellknight-" + (object.getVariant() == 2 ? "64" : "texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(HellknightEntity object) {
		return DoomMod.modResource("animations/baron_hell_animation.json");
	}

	@Override
	public void setCustomAnimations(HellknightEntity animatable, long instanceId, AnimationState<HellknightEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("neck");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(HellknightEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}