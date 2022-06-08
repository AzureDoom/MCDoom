package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BaronModel extends AnimatedTickingGeoModel<BaronEntity> {

	@Override
	public ResourceLocation getModelResource(BaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/baron.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BaronEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/baronofhell-" + (object.getVariant() == 2 ? "green" : "texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(BaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(BaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}