package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SpectreModel extends GeoModel<SpectreEntity> {

	@Override
	public Identifier getModelResource(SpectreEntity object) {
		return new Identifier(DoomMod.MODID, "geo/" + (object.getVariant() > 1 ? "pinky" : "pinky2016") + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(SpectreEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/" + (object.getVariant() == 2 ? "pinky-texturemap"
				: object.getVariant() == 3 ? "pinky_green" : "pinky2016") + ".png");
	}

	@Override
	public Identifier getAnimationResource(SpectreEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() > 1 ? "pinky_" : "pinky2016.") + "animation.json");
	}

	@Override
	public void setCustomAnimations(SpectreEntity animatable, long instanceId,
			AnimationState<SpectreEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + (animatable.getVariant() == 1 ? 270 : 30)) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 500F));
		}
	}

	@Override
	public RenderLayer getRenderType(SpectreEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}