package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3q.model.provider.data.EntityModelData;

public class ProwlerModel extends AnimatedTickingGeoModel<ProwlerEntity> {

	@Override
	public Identifier getModelResource(ProwlerEntity object) {
		return new Identifier(DoomMod.MODID, "geo/prowler.geo.json");
	}

	@Override
	public Identifier getTextureResource(ProwlerEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "prowler_cursed" : "prowler") + ".png");
	}

	@Override
	public Identifier getAnimationResource(ProwlerEntity object) {
		return new Identifier(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@Override
	public void setLivingAnimations(ProwlerEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X
							.getRadialQuaternion(Vec3f.POSITIVE_X
									.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX())
							.getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y
							.getRadialQuaternion(Vec3f.POSITIVE_Y
									.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY())
							.getY());
		}
	}
}