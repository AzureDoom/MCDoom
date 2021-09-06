package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MarauderModel extends AnimatedGeoModel<MarauderEntity> {

	public MarauderModel() {
	}

	@Override
	public ResourceLocation getModelLocation(MarauderEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/marauder.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MarauderEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/marauder.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MarauderEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/marauder.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(MarauderEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bipedHead");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}