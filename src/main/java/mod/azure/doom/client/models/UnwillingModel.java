package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class UnwillingModel extends AnimatedTickingGeoModel<UnwillingEntity> {

	@Override
	public ResourceLocation getModelResource(UnwillingEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/unwilling.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(UnwillingEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/unwilling.png");
	}

	@Override
	public ResourceLocation getAnimationResource(UnwillingEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@Override
	public void setLivingAnimations(UnwillingEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}

}