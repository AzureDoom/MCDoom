package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.NightmareImpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpNightmareModel extends AnimatedGeoModel<NightmareImpEntity> {

	public ImpNightmareModel() {
	}

	@Override
	public ResourceLocation getModelLocation(NightmareImpEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/nightmareimp.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(NightmareImpEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/nightmareimp-texture.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(NightmareImpEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/imp_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(NightmareImpEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 360F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
	}
}