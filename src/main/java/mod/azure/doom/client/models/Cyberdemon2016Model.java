package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.Cyberdemon2016Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Cyberdemon2016Model extends AnimatedGeoModel<Cyberdemon2016Entity> {

	@Override
	public ResourceLocation getModelLocation(Cyberdemon2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cyberdemon2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Cyberdemon2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/cyberdemon2016.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Cyberdemon2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/cyberdemon2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Cyberdemon2016Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch - 80) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
	}
}