package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SpectreModel extends AnimatedGeoModel<SpectreEntity> {

	@Override
	public ResourceLocation getModelLocation(SpectreEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"geo/" + (object.getVariant() > 1 ? "pinky" : "pinky2016") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SpectreEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 2 ? "pinky-texturemap"
				: object.getVariant() == 3 ? "pinky_green" : "pinky2016") + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SpectreEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"animations/" + (object.getVariant() > 1 ? "pinky_" : "pinky2016.") + "animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(SpectreEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + (entity.getVariant() == 1 ? 90 : 30)) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
	}
}