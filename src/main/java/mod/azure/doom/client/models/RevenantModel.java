package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RevenantModel extends GeoModel<RevenantEntity> {

	@Override
	public ResourceLocation getModelResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_nojetpack.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@Override
	public void setCustomAnimations(RevenantEntity animatable, long instanceId,
			AnimationState<RevenantEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(RevenantEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}