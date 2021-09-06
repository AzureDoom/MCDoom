package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RevenantModel extends AnimatedGeoModel<RevenantEntity> {

	public RevenantModel() {
	}

	@Override
	public Identifier getModelLocation(RevenantEntity object) {
		return new Identifier(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public Identifier getTextureLocation(RevenantEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/revenant.png");
	}

	@Override
	public Identifier getAnimationFileLocation(RevenantEntity object) {
		return new Identifier(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(RevenantEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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