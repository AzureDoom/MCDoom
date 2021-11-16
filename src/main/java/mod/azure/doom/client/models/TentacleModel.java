package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TentacleModel extends AnimatedTickingGeoModel<TentacleEntity> {

	@Override
	public ResourceLocation getModelLocation(TentacleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/tentacle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TentacleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/tentacle.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TentacleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/tentacle.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(TentacleEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bone3");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch - 30) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
	}
}