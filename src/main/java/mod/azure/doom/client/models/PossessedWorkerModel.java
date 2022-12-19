package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PossessedWorkerModel extends GeoModel<PossessedScientistEntity> {

	@Override
	public Identifier getModelResource(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "geo/possessedworker.geo.json");
	}

	@Override
	public Identifier getTextureResource(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/possessedworker.png");
	}

	@Override
	public Identifier getAnimationResource(PossessedScientistEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@Override
	public void setCustomAnimations(PossessedScientistEntity animatable, long instanceId,
			AnimationState<PossessedScientistEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("Head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(PossessedScientistEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}