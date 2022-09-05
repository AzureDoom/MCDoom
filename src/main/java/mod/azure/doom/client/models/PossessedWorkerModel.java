package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3q.model.provider.data.EntityModelData;

public class PossessedWorkerModel extends AnimatedTickingGeoModel<PossessedScientistEntity> {

	@Override
	public Identifier getModelResource(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "geo/possessedworker.geo.json");
	}

	@Override
	public Identifier getTextureResource(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/possessedworker.png");
	}

	@Override
	public Identifier getAnimationResource(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

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