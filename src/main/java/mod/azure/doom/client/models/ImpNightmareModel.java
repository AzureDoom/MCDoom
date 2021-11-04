package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.NightmareImpEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpNightmareModel extends AnimatedTickingGeoModel<NightmareImpEntity> {

	public ImpNightmareModel() {
	}

	@Override
	public Identifier getModelLocation(NightmareImpEntity object) {
		return new Identifier(DoomMod.MODID, "geo/nightmareimp.geo.json");
	}

	@Override
	public Identifier getTextureLocation(NightmareImpEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/nightmareimp-texture.png");
	}

	@Override
	public Identifier getAnimationFileLocation(NightmareImpEntity object) {
		return new Identifier(DoomMod.MODID, "animations/imp_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(NightmareImpEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch - 5) * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}