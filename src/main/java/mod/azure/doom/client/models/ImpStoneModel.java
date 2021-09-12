package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpStoneModel extends AnimatedGeoModel<ImpStoneEntity> {

	public ImpStoneModel() {
	}

	@Override
	public ResourceLocation getModelLocation(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/imp2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/stoneimp.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ImpStoneEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
	}
}