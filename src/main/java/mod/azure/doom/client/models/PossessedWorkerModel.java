package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedWorkerModel extends AnimatedGeoModel<PossessedScientistEntity> {

	public PossessedWorkerModel() {
	}

	@Override
	public ResourceLocation getModelLocation(PossessedScientistEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/possessedworker.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PossessedScientistEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/possessedworker.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PossessedScientistEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PossessedScientistEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 360F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
		}
	}
}