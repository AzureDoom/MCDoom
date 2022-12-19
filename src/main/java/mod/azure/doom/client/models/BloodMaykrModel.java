package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BloodMaykrModel extends GeoModel<BloodMaykrEntity> {

	@Override
	public Identifier getModelResource(BloodMaykrEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bloodmaykr.geo.json");
	}

	@Override
	public Identifier getTextureResource(BloodMaykrEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/bloodmaykr.png");
	}

	@Override
	public Identifier getAnimationResource(BloodMaykrEntity object) {
		return new Identifier(DoomMod.MODID, "animations/bloodmaykr.animation.json");
	}

	@Override
	public void setCustomAnimations(BloodMaykrEntity animatable, long instanceId,
			AnimationState<BloodMaykrEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(BloodMaykrEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}