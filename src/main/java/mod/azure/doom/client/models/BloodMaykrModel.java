package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BloodMaykrModel extends AnimatedTickingGeoModel<BloodMaykrEntity> {

	@Override
	public ResourceLocation getModelLocation(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bloodmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/bloodmaykr.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/bloodmaykr.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(BloodMaykrEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}

}