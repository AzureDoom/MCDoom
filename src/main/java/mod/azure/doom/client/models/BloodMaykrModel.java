package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BloodMaykrModel extends GeoModel<BloodMaykrEntity> {

	@Override
	public ResourceLocation getModelResource(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bloodmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/bloodmaykr.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/bloodmaykr.animation.json");
	}

	@Override
	public void setCustomAnimations(BloodMaykrEntity animatable, long instanceId,
			AnimationState<BloodMaykrEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(BloodMaykrEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}