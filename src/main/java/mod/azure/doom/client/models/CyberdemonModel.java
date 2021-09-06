package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CyberdemonModel extends AnimatedGeoModel<CyberdemonEntity> {

	public CyberdemonModel() {
	}

	@Override
	public Identifier getModelLocation(CyberdemonEntity object) {
		return new Identifier(DoomMod.MODID, "geo/cyberdemon.geo.json");
	}

	@Override
	public Identifier getTextureLocation(CyberdemonEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/cyberdemon-texturemap.png");
	}

	@Override
	public Identifier getAnimationFileLocation(CyberdemonEntity object) {
		return new Identifier(DoomMod.MODID, "animations/cyberdemon_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(CyberdemonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch - 30) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}