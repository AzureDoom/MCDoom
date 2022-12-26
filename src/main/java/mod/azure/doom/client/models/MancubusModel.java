package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MancubusModel extends GeoModel<MancubusEntity> {

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
	public void setCustomAnimations(MancubusEntity animatable, long instanceId,
			AnimationState<MancubusEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(MancubusEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}