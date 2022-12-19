package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ImpStoneModel extends GeoModel<ImpStoneEntity> {

	@Override
	public Identifier getModelResource(ImpStoneEntity object) {
		return new Identifier(DoomMod.MODID, "geo/imp2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(ImpStoneEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/stoneimp.png");
	}

	@Override
	public Identifier getAnimationResource(ImpStoneEntity object) {
		return new Identifier(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@Override
	public void setCustomAnimations(ImpStoneEntity animatable, long instanceId,
			AnimationState<ImpStoneEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(ImpStoneEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}