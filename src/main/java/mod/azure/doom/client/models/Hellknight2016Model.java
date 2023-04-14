package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class Hellknight2016Model extends GeoModel<Hellknight2016Entity> {

	@Override
	public ResourceLocation getModelResource(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/hellknight2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/hellknight2016.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/hellknight2016_animation.json");
	}

	@Override
	public void setCustomAnimations(Hellknight2016Entity animatable, long instanceId, AnimationState<Hellknight2016Entity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("neck");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 85) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(Hellknight2016Entity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}