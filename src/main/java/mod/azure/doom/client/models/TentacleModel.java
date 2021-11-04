package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TentacleModel extends AnimatedTickingGeoModel<TentacleEntity> {

	@Override
	public Identifier getModelLocation(TentacleEntity object) {
		return new Identifier(DoomMod.MODID, "geo/tentacle.geo.json");
	}

	@Override
	public Identifier getTextureLocation(TentacleEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/tentacle.png");
	}

	@Override
	public Identifier getAnimationFileLocation(TentacleEntity object) {
		return new Identifier(DoomMod.MODID, "animations/tentacle.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(TentacleEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bone3");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch - 30) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 360F)).getY());
		}
	}
}