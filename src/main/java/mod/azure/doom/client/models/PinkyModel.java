package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PinkyModel extends AnimatedGeoModel<PinkyEntity> {

	public PinkyModel() {
	}

	@Override
	public Identifier getModelLocation(PinkyEntity object) {
		return new Identifier(DoomMod.MODID, "geo/pinky.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PinkyEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 1 ? "pinky-texturemap" : "pinky_green") + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(PinkyEntity object) {
		return new Identifier(DoomMod.MODID, "animations/pinky_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PinkyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion((extraData.headPitch + 30) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 500F)).getY());
		}
	}
}