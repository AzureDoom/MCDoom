package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArchMaykrModel extends GeoModel<ArchMakyrEntity> {

	@Override
	public Identifier getModelResource(ArchMakyrEntity object) {
		return new Identifier(DoomMod.MODID, "geo/archmaykr_" + object.getVariant() + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(ArchMakyrEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/archmaykr_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationResource(ArchMakyrEntity object) {
		return new Identifier(DoomMod.MODID, "animations/archmaykr_" + object.getVariant() + ".animation.json");
	}

	@Override
	public void setCustomAnimations(ArchMakyrEntity animatable, long instanceId, AnimationState<ArchMakyrEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(ArchMakyrEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}