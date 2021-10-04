package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SpectreModel extends AnimatedGeoModel<SpectreEntity> {

	@Override
	public Identifier getModelLocation(SpectreEntity object) {
		return new Identifier(DoomMod.MODID, "geo/" + (object.getVariant() > 1 ? "pinky" : "pinky2016") + ".geo.json");
	}

	@Override
	public Identifier getTextureLocation(SpectreEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 2 ? "pinky-texturemap"
				: object.getVariant() == 3 ? "pinky_green" : "pinky2016") + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(SpectreEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() > 1 ? "pinky_" : "pinky2016.") + "animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(SpectreEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch + (entity.getVariant() == 1 ? 270 : 30)) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 500F)).getY());
		}
	}
}