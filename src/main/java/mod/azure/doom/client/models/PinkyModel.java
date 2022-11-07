package mod.azure.doom.client.models;

import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PinkyModel extends AnimatedTickingGeoModel<PinkyEntity> {

	@Override
	public ResourceLocation getModelLocation(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"geo/" + (object.getVariant() == 3 ? "pinky2016" : "pinky") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 4 ? "pinky_green"
				: object.getVariant() == 2 ? "pinky-64" : object.getVariant() == 3 ? "pinky2016" : "pinky-texturemap")
				+ ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PinkyEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "pinky2016." : "pinky_") + "animation.json");
	}

	@Override
	public void setLivingAnimations(PinkyEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP
					.rotation((extraData.headPitch + (entity.getVariant() == 3 ? 180 : 30)) * ((float) Math.PI / 360F))
					.i());
			head.setRotationY(Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 500F)).j());
		}
	}
}