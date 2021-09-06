package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Pinky2016;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Pinky2016Model extends AnimatedGeoModel<Pinky2016> {
	
	@Override
	public Identifier getModelLocation(Pinky2016 object) {
		return new Identifier(DoomMod.MODID, "geo/pinky2016.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Pinky2016 object) {
		return new Identifier(DoomMod.MODID, "textures/entity/pinky2016.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Pinky2016 object) {
		return new Identifier(DoomMod.MODID, "animations/pinky2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Pinky2016 entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch + 30) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 500F)).getY());
		}
	}
}