package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MancubusModel extends AnimatedGeoModel<MancubusEntity> {

	public MancubusModel() {
	}

	@Override
	public ResourceLocation getModelLocation(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mancubus.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/mancubus.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/mancubus_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(MancubusEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}