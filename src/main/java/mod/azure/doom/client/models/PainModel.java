package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PainModel extends GeoModel<PainEntity> {

	@Override
	public Identifier getModelResource(PainEntity object) {
		return new Identifier(DoomMod.MODID,
				"geo/" + (object.getVariant() == 2 ? "pain64" : object.getVariant() == 3 ? "paineternal" : "pain")
						+ ".geo.json");
	}

	@Override
	public Identifier getTextureResource(PainEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/"
				+ (object.getVariant() == 2
						? (object.getAttckingState() == 2 ? "painelemental64-attacking" : "painelemental64-normal")
						: object.getVariant() == 3 ? "paineternal"
								: (object.getAttckingState() == 1 ? "painelemental-attacking" : "painelemental-normal"))
				+ ".png");
	}

	@Override
	public Identifier getAnimationResource(PainEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "paineternal." : "pain_") + "animation.json");
	}

	@Override
	public void setCustomAnimations(PainEntity animatable, long instanceId,
			AnimationState<PainEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("body");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(PainEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}