package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class IconofsinModel extends GeoModel<IconofsinEntity> {

	@Override
	public ResourceLocation getModelResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/icon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/iconofsin.png");
	}

	@Override
	public ResourceLocation getAnimationResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/icon.animation.json");
	}

	@Override
	public void setCustomAnimations(IconofsinEntity animatable, long instanceId,
			AnimationState<IconofsinEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() - 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 360F));
		}
	}

	@Override
	public RenderType getRenderType(IconofsinEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}