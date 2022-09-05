package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BloodMaykrModel extends AnimatedTickingGeoModel<BloodMaykrEntity> {

	@Override
	public Identifier getModelResource(BloodMaykrEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bloodmaykr.geo.json");
	}

	@Override
	public Identifier getTextureResource(BloodMaykrEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/bloodmaykr.png");
	}

	@Override
	public Identifier getAnimationResource(BloodMaykrEntity object) {
		return new Identifier(DoomMod.MODID, "animations/bloodmaykr.animation.json");
	}

	@Override
	public void setLivingAnimations(BloodMaykrEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}

}