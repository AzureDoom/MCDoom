package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ImpModel extends GeoModel<ImpEntity> {

	public ResourceLocation classic_model = DoomMod.modResource("geo/imp.geo.json");
	public ResourceLocation nightmareimp_model = DoomMod.modResource("geo/nightmareimp.geo.json");
	public ResourceLocation imp2016_model = DoomMod.modResource("geo/imp2016.geo.json");

	public ResourceLocation classic_texture = DoomMod.modResource("textures/entity/imp-texturemap.png");
	public ResourceLocation d64_texture = DoomMod.modResource("textures/entity/imp-64.png");
	public ResourceLocation nightmareimp_texture = DoomMod.modResource("textures/entity/nightmareimp-texture.png");
	public ResourceLocation imp2016_texture = DoomMod.modResource("textures/entity/imp2016.png");

	public ResourceLocation imp2016_animation = DoomMod.modResource("animations/imp2016.animation.json");
	public ResourceLocation imp_animation = DoomMod.modResource("animations/imp_animation.json");

	@Override
	public ResourceLocation getModelResource(ImpEntity object) {
		return object.getVariant() == 2 ? nightmareimp_model : object.getVariant() == 3 ? nightmareimp_model : object.getVariant() == 4 ? imp2016_model : classic_model;
	}

	@Override
	public ResourceLocation getTextureResource(ImpEntity object) {
		return object.getVariant() == 2 ? nightmareimp_texture : object.getVariant() == 3 ? d64_texture : object.getVariant() == 4 ? imp2016_texture : classic_texture;
	}

	@Override
	public ResourceLocation getAnimationResource(ImpEntity object) {
		return object.getVariant() == 4 ? imp2016_animation : imp_animation;
	}

	@Override
	public void setCustomAnimations(ImpEntity animatable, long instanceId, AnimationState<ImpEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("head");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() - 5) * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(ImpEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}