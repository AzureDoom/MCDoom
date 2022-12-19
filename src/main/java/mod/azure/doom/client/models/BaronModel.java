package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BaronModel extends GeoModel<BaronEntity> {

	public BaronModel() {
	}

	@Override
	public Identifier getModelResource(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron.geo.json");
	}

	@Override
	public Identifier getTextureResource(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/baronofhell-"
				+ (object.getVariant() == 2 ? "green" : object.getVariant() == 3 ? "64" : "texturemap") + ".png");
	}

	@Override
	public Identifier getAnimationResource(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@Override
	public void setCustomAnimations(BaronEntity animatable, long instanceId,
			AnimationState<BaronEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(BaronEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}