package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GargoyleModel extends GeoModel<GargoyleEntity> {

	public GargoyleModel() {
	}

	@Override
	public Identifier getModelResource(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gargoyleimp.geo.json");
	}

	@Override
	public Identifier getTextureResource(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/gargoyleimp.png");
	}

	@Override
	public Identifier getAnimationResource(GargoyleEntity object) {
		return new Identifier(DoomMod.MODID, "animations/gargoyleimp.animation.json");
	}

	@Override
	public void setCustomAnimations(GargoyleEntity animatable, long instanceId,
			AnimationState<GargoyleEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(GargoyleEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}