package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ArchMaykrModel extends AnimatedTickingGeoModel<ArchMakyrEntity> {

	@Override
	public ResourceLocation getModelLocation(ArchMakyrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archmaykr_" + object.getVariant() + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ArchMakyrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/archmaykr_" + object.getVariant() + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ArchMakyrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/archmaykr_" + object.getVariant() + ".animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ArchMakyrEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}

}