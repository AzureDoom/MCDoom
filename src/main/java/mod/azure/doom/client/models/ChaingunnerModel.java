package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ChaingunnerModel extends GeoModel<ChaingunnerEntity> {

	public ChaingunnerModel() {
	}

	@Override
	public Identifier getModelResource(ChaingunnerEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public Identifier getTextureResource(ChaingunnerEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/chaingunner.png");
	}

	@Override
	public Identifier getAnimationResource(ChaingunnerEntity object) {
		return new Identifier(DoomMod.MODID, "animations/chaingunner.animation.json");
	}

	@Override
	public void setCustomAnimations(ChaingunnerEntity animatable, long instanceId,
			AnimationState<ChaingunnerEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(ChaingunnerEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}