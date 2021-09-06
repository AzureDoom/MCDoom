package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedSoldierModel extends AnimatedGeoModel<PossessedSoldierEntity> {

	public PossessedSoldierModel() {
	}

	@Override
	public Identifier getModelLocation(PossessedSoldierEntity object) {
		return new Identifier(DoomMod.MODID, "geo/possessedsoldier.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PossessedSoldierEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PossessedSoldierEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessedsoldier.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PossessedSoldierEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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