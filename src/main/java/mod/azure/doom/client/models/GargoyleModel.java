package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GargoyleModel extends AnimatedGeoModel<GargoyleEntity> {

	public GargoyleModel() {
	}

	@Override
	public Identifier getModelLocation(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gargoyleimp.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/gargoyleimp.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GargoyleEntity object) {
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