package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.Imp2016Entity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Imp2016Model extends AnimatedGeoModel<Imp2016Entity> {

	public Imp2016Model() {
	}

	@Override
	public Identifier getModelLocation(Imp2016Entity object) {
		return new Identifier(DoomMod.MODID, "geo/imp2016.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Imp2016Entity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/imp2016.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Imp2016Entity object) {
		return new Identifier(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Imp2016Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
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