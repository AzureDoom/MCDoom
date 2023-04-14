package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * LostSoul - __Botmon__
 */
public class LostSoulModel extends GeoModel<LostSoulEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_1.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_2.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_3.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_4.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_6.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_7.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_8.png") };

	private static final ResourceLocation[] TEX1 = { new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_1.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_2.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_3.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_4.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_6.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_7.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_8.png") };

	private static final ResourceLocation[] TEX64 = { new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_1.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_2.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_3.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_4.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_6.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_7.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_64_fire_8.png") };

	@Override
	public ResourceLocation getModelResource(LostSoulEntity object) {
		return object.getVariant() == 3 ? new ResourceLocation(DoomMod.MODID, "geo/lostsoul64.geo.json") : new ResourceLocation(DoomMod.MODID, "geo/lostsoul.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LostSoulEntity object) {
		return object.getVariant() == 2 ? TEX1[object.getFlameTimer()] : object.getVariant() == 3 ? TEX64[object.getFlameTimer()] : TEX[object.getFlameTimer()];
	}

	@Override
	public ResourceLocation getAnimationResource(LostSoulEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/lostsoul_animation.json");
	}

	@Override
	public void setCustomAnimations(LostSoulEntity animatable, long instanceId, AnimationState<LostSoulEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone head = getAnimationProcessor().getBone("head");
		final EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(LostSoulEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}