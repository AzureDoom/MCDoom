package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GladiatorModel extends GeoModel<GladiatorEntity> {

	@Override
	public Identifier getModelResource(GladiatorEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gladiator.geo.json");
	}

	@Override
	public Identifier getTextureResource(GladiatorEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/" + (object.getTextureState() == 1 ? "gladiator_1"
				: object.getTextureState() == 2 ? "gladiator_2" : "gladiator") + ".png");
	}

	@Override
	public Identifier getAnimationResource(GladiatorEntity object) {
		return new Identifier(DoomMod.MODID, "animations/gladiator.animation.json");
	}

	@Override
	public void setCustomAnimations(GladiatorEntity animatable, long instanceId,
			AnimationState<GladiatorEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(GladiatorEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}