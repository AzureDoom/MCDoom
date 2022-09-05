package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MarauderModel extends AnimatedTickingGeoModel<MarauderEntity> {

	@Override
	public Identifier getModelLocation(MarauderEntity object) {
		return new Identifier(DoomMod.MODID, "geo/marauder.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MarauderEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/marauder.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MarauderEntity object) {
		return new Identifier(DoomMod.MODID, "animations/marauder.animation.json");
	}

	@Override
	public void setLivingAnimations(MarauderEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}
}