package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

/**
 * LostSoul - __Botmon__
 */
public class LostSoulModel extends AnimatedTickingGeoModel<LostSoulEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_7.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_fire_8.png") };

	private static final ResourceLocation[] TEX1 = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_7.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lost_soul_green_fire_8.png") };

	@Override
	public ResourceLocation getModelResource(LostSoulEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/lostsoul.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LostSoulEntity object) {
		return object.getVariant() == 2 ? TEX1[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(LostSoulEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/lostsoul_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(LostSoulEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}