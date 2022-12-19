package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * LostSoul - __Botmon__
 */
public class LostSoulModel extends GeoModel<LostSoulEntity> {

	private static final Identifier[] TEX = { new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_fire_8.png") };

	private static final Identifier[] TEX1 = {
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_green_fire_8.png") };

	private static final Identifier[] TEX64 = {
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lost_soul_64_fire_8.png") };

	@Override
	public Identifier getModelResource(LostSoulEntity object) {
		return object.getVariant() == 3 ? new Identifier(DoomMod.MODID, "geo/lostsoul64.geo.json")
				: new Identifier(DoomMod.MODID, "geo/lostsoul.geo.json");
	}

	@Override
	public Identifier getTextureResource(LostSoulEntity object) {
		return object.getVariant() == 2 ? TEX1[(object.getFlameTimer())]
				: object.getVariant() == 3 ? TEX64[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationResource(LostSoulEntity object) {
		return new Identifier(DoomMod.MODID, "animations/lostsoul_animation.json");
	}

	@Override
	public void setCustomAnimations(LostSoulEntity animatable, long instanceId,
			AnimationState<LostSoulEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(LostSoulEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}