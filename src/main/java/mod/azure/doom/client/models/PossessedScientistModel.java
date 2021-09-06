package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedScientistModel extends AnimatedGeoModel<PossessedScientistEntity> {

	public PossessedScientistModel() {
	}

	@Override
	public Identifier getModelLocation(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "geo/scientistpossessed.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/possessedscientist.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PossessedScientistEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}