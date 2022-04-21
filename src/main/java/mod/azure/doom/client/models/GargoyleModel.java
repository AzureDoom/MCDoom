package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3q.model.provider.data.EntityModelData;

public class GargoyleModel extends AnimatedTickingGeoModel<GargoyleEntity> {

	public GargoyleModel() {
	}

	@Override
	public Identifier getModelResource(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gargoyleimp.geo.json");
	}

	@Override
	public Identifier getTextureResource(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/gargoyleimp.png");
	}

	@Override
	public Identifier getAnimationResource(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "animations/gargoyleimp.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(GargoyleEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}