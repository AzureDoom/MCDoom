package mod.azure.doom.client.models;

import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MancubusModel extends AnimatedTickingGeoModel<MancubusEntity> {

	public String classic = "mancubus";
	public String classiccyber = "cyber_mancubus";
	public String d64 = "mancubus64";
	public String d2016 = "mancubus2016";
	public String d2016cyber = "cybermancubus2016";

	@Override
	public ResourceLocation getModelResource(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/"
				+ (object.getVariant() == 2 ? d64
						: object.getVariant() == 3 ? d2016
								: object.getVariant() == 4 ? classic : object.getVariant() == 5 ? d2016 : classic)
				+ ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 2 ? d64
				: object.getVariant() == 3 ? d2016
						: object.getVariant() == 4 ? classiccyber : object.getVariant() == 5 ? d2016cyber : classic)
				+ ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/mancubus_animation.json");
	}

	@Override
	public void setLivingAnimations(MancubusEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}
}