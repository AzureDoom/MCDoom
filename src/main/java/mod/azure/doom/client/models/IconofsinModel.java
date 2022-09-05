package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3q.model.provider.data.EntityModelData;

public class IconofsinModel extends AnimatedTickingGeoModel<IconofsinEntity> {
	
	@Override
	public Identifier getModelResource(IconofsinEntity object) {
		return new Identifier(DoomMod.MODID, "geo/icon.geo.json");
	}

	@Override
	public Identifier getTextureResource(IconofsinEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/iconofsin.png");
	}

	@Override
	public Identifier getAnimationResource(IconofsinEntity object) {
		return new Identifier(DoomMod.MODID, "animations/icon.animation.json");
	}

	@Override
	public void setLivingAnimations(IconofsinEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch - 20) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 360F)).getY());
		}
	}
}