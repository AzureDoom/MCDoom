package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MechaZombieModel extends GeoModel<MechaZombieEntity> {

	@Override
	public Identifier getModelResource(MechaZombieEntity object) {
		return new Identifier(DoomMod.MODID, "geo/mechazombie.geo.json");
	}

	@Override
	public Identifier getTextureResource(MechaZombieEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/mechazombie.png");
	}

	@Override
	public Identifier getAnimationResource(MechaZombieEntity object) {
		return new Identifier(DoomMod.MODID, "animations/mechazombie_animation.json");
	}

	@Override
	public void setCustomAnimations(MechaZombieEntity animatable, long instanceId,
			AnimationState<MechaZombieEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(MechaZombieEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}