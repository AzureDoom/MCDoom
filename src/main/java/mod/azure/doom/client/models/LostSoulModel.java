package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

/**
 * LostSoul - __Botmon__
 */
public class LostSoulModel extends AnimatedTickingGeoModel<LostSoulEntity> {

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
	public Identifier getModelLocation(LostSoulEntity object) {
		return object.getVariant() == 3 ? new Identifier(DoomMod.MODID, "geo/lostsoul64.geo.json")
				: new Identifier(DoomMod.MODID, "geo/lostsoul.geo.json");
	}

	@Override
	public Identifier getTextureLocation(LostSoulEntity object) {
		return object.getVariant() == 2 ? TEX1[(object.getFlameTimer())]
				: object.getVariant() == 3 ? TEX64[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationFileLocation(LostSoulEntity object) {
		return new Identifier(DoomMod.MODID, "animations/lostsoul_animation.json");
	}

	@Override
	public void setLivingAnimations(LostSoulEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}
}