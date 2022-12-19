package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class UnwillingModel extends GeoModel<UnwillingEntity> {

	@Override
	public Identifier getModelResource(UnwillingEntity object) {
		return new Identifier(DoomMod.MODID, "geo/unwilling.geo.json");
	}

	@Override
	public Identifier getTextureResource(UnwillingEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/unwilling.png");
	}

	@Override
	public Identifier getAnimationResource(UnwillingEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@Override
	public void setCustomAnimations(UnwillingEntity animatable, long instanceId,
			AnimationState<UnwillingEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("Head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(UnwillingEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}