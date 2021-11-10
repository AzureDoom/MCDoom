package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.GladiatorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GladiatorModel extends AnimatedTickingGeoModel<GladiatorEntity> {

	@Override
	public ResourceLocation getModelLocation(GladiatorEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/gladiator.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GladiatorEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/gladiator.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(GladiatorEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/gladiator.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(GladiatorEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}