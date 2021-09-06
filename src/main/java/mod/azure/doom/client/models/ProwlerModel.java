package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ProwlerModel extends AnimatedGeoModel<ProwlerEntity> {

	@Override
	public ResourceLocation getModelLocation(ProwlerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/prowler.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ProwlerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/prowler.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ProwlerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ProwlerEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
	}
}