package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3q.model.provider.data.EntityModelData;

public class PainModel extends AnimatedTickingGeoModel<PainEntity> {

	@Override
	public Identifier getModelResource(PainEntity object) {
		return new Identifier(DoomMod.MODID,
				"geo/" + (object.getVariant() == 2 ? "pain64" : object.getVariant() == 3 ? "paineternal" : "pain")
						+ ".geo.json");
	}

	@Override
	public Identifier getTextureResource(PainEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/"
				+ (object.getVariant() == 2
						? (object.getAttckingState() == 2 ? "painelemental64-attacking" : "painelemental64-normal")
						: object.getVariant() == 3 ? "paineternal"
								: (object.getAttckingState() == 1 ? "painelemental-attacking" : "painelemental-normal"))
				+ ".png");
	}

	@Override
	public Identifier getAnimationResource(PainEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "paineternal." : "pain_") + "animation.json");
	}

	@Override
	public void setLivingAnimations(PainEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("body");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}
}