package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ShotgunguyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ShotgunguyModel extends GeoModel<ShotgunguyEntity> {

	@Override
	public Identifier getModelResource(ShotgunguyEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShotgunguyEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "shotgunguy64" : "shotgunguy") + ".png");
	}

	@Override
	public Identifier getAnimationResource(ShotgunguyEntity object) {
		return new Identifier(DoomMod.MODID, "animations/shotgunzombie.animation.json");
	}

	@Override
	public void setCustomAnimations(ShotgunguyEntity animatable, long instanceId,
			AnimationState<ShotgunguyEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("bipedHead");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(ShotgunguyEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}