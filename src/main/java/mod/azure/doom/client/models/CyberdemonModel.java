package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CyberdemonModel extends AnimatedTickingGeoModel<CyberdemonEntity> {

	public CyberdemonModel() {
	}

	@Override
	public ResourceLocation getModelLocation(CyberdemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cyberdemon.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CyberdemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/cyberdemon-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CyberdemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/cyberdemon_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(CyberdemonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch - 30) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}