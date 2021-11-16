package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpModel extends AnimatedTickingGeoModel<ImpEntity> {

	public ImpModel() {
	}

	@Override
	public ResourceLocation getModelLocation(ImpEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/imp.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ImpEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/imp-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ImpEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/imp_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ImpEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 360F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
	}
}