package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class UnwillingModel extends AnimatedTickingGeoModel<UnwillingEntity> {

	public UnwillingModel() {
	}

	@Override
	public Identifier getModelResource(UnwillingEntity object) {
		return new Identifier(DoomMod.MODID, "geo/unwilling.geo.json");
	}

	@Override
	public Identifier getTextureResource(UnwillingEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/unwilling.png");
	}

	@Override
	public Identifier getAnimationResource(UnwillingEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(UnwillingEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}

}