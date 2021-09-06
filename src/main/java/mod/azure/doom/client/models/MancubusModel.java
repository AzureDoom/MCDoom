package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MancubusModel extends AnimatedGeoModel<MancubusEntity> {

	public MancubusModel() {
	}

	@Override
	public Identifier getModelLocation(MancubusEntity object) {
		return new Identifier(DoomMod.MODID, "geo/mancubus.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MancubusEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/mancubus.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MancubusEntity object) {
		return new Identifier(DoomMod.MODID, "animations/mancubus_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(MancubusEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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