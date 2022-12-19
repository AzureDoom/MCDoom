package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PinkyModel extends GeoModel<PinkyEntity> {

	@Override
	public Identifier getModelResource(PinkyEntity object) {
		return new Identifier(DoomMod.MODID, "geo/" + (object.getVariant() == 3 ? "pinky2016" : "pinky") + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(PinkyEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 4 ? "pinky_green"
				: object.getVariant() == 2 ? "pinky-64"
						:object.getVariant() == 3 ? "pinky2016" : "pinky-texturemap") + ".png");
	}

	@Override
	public Identifier getAnimationResource(PinkyEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "pinky2016." : "pinky_") + "animation.json");
	}

	@Override
	public void setCustomAnimations(PinkyEntity animatable, long instanceId,
			AnimationState<PinkyEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + (animatable.getVariant() == 3 ? 180 : 30)) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 500F));
		}
	}

	@Override
	public RenderLayer getRenderType(PinkyEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}