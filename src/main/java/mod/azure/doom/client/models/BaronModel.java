package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BaronModel extends AnimatedGeoModel<BaronEntity> {

	public BaronModel() {
	}

	@Override
	public Identifier getModelLocation(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BaronEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/baronofhell-" + (object.getVariant() == 2 ? "green" : "texturemap") + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(BaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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