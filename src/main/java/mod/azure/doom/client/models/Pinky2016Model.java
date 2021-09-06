package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Pinky2016;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Pinky2016Model extends AnimatedGeoModel<Pinky2016> {

	@Override
	public ResourceLocation getModelLocation(Pinky2016 object) {
		return new ResourceLocation(DoomMod.MODID, "geo/pinky2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Pinky2016 object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/pinky2016.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Pinky2016 object) {
		return new ResourceLocation(DoomMod.MODID, "animations/pinky2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Pinky2016 entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
	}
}