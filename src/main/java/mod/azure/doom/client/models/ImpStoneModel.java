package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ImpStoneModel extends GeoModel<ImpStoneEntity> {

	@Override
	public ResourceLocation getModelResource(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/imp2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/stoneimp.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@Override
	public void setCustomAnimations(ImpStoneEntity animatable, long instanceId,
			AnimationState<ImpStoneEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(ImpStoneEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}