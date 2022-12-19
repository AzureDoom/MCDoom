package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TentacleModel extends GeoModel<TentacleEntity> {

	@Override
	public Identifier getModelResource(TentacleEntity object) {
		return new Identifier(DoomMod.MODID, "geo/tentacle.geo.json");
	}

	@Override
	public Identifier getTextureResource(TentacleEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/tentacle.png");
	}

	@Override
	public Identifier getAnimationResource(TentacleEntity object) {
		return new Identifier(DoomMod.MODID, "animations/tentacle.animation.json");
	}

	@Override
	public void setCustomAnimations(TentacleEntity animatable, long instanceId,
			AnimationState<TentacleEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("bone3");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() - 30) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 360F));
		}
	}

	@Override
	public RenderLayer getRenderType(TentacleEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}