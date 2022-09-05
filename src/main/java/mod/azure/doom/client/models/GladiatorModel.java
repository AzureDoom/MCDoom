package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3q.model.provider.data.EntityModelData;

public class GladiatorModel extends AnimatedTickingGeoModel<GladiatorEntity> {

	@Override
	public Identifier getModelResource(GladiatorEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gladiator.geo.json");
	}

	@Override
	public Identifier getTextureResource(GladiatorEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/" + (object.getTextureState() == 1 ? "gladiator_1"
				: object.getTextureState() == 2 ? "gladiator_2" : "gladiator") + ".png");
	}

	@Override
	public Identifier getAnimationResource(GladiatorEntity object) {
		return new Identifier(DoomMod.MODID, "animations/gladiator.animation.json");
	}
	
	@Override
	public void setLivingAnimations(GladiatorEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}