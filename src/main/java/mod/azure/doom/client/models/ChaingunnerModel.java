package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ChaingunnerModel extends AnimatedTickingGeoModel<ChaingunnerEntity> {

	public ChaingunnerModel() {
	}

	@Override
	public Identifier getModelLocation(ChaingunnerEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ChaingunnerEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/chaingunner.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ChaingunnerEntity object) {
		return new Identifier(DoomMod.MODID, "animations/chaingunner.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ChaingunnerEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bipedHead");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}
}