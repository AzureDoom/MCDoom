package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MarauderModel extends GeoModel<MarauderEntity> {

	@Override
	public Identifier getModelResource(MarauderEntity object) {
		return new Identifier(DoomMod.MODID, "geo/marauder.geo.json");
	}

	@Override
	public Identifier getTextureResource(MarauderEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/marauder.png");
	}

	@Override
	public Identifier getAnimationResource(MarauderEntity object) {
		return new Identifier(DoomMod.MODID, "animations/marauder.animation.json");
	}

	@Override
	public void setCustomAnimations(MarauderEntity animatable, long instanceId,
			AnimationState<MarauderEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(MarauderEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}