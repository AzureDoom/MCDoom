package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CacodemonModel extends GeoModel<CacodemonEntity> {

	@Override
	public Identifier getModelResource(CacodemonEntity object) {
		return new Identifier(DoomMod.MODID, "geo/" + (object.getVariant() == 1 ? "cacodemon64"
				: object.getVariant() >= 3 ? "cacodemoneternal" : "cacodemon") + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(CacodemonEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 1 ? "cacodemon64"
						: object.getVariant() == 3 ? "cacodemoneternal"
								: object.getVariant() == 4 ? "cacodemon2016" : "cacodemon")
						+ ".png");
	}

	@Override
	public Identifier getAnimationResource(CacodemonEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "cacodemoneternal." : "cacodemon_") + "animation.json");
	}

	@Override
	public void setCustomAnimations(CacodemonEntity animatable, long instanceId, AnimationState<CacodemonEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("body");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(CacodemonEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}