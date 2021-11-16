package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class UnwillingModel extends AnimatedTickingGeoModel<UnwillingEntity> {

	public UnwillingModel() {
	}

	@Override
	public ResourceLocation getModelLocation(UnwillingEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/unwilling.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(UnwillingEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/unwilling.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(UnwillingEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(UnwillingEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}

}