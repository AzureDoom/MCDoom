package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedSoldierModel extends AnimatedGeoModel<PossessedSoldierEntity> {

	public PossessedSoldierModel() {
	}

	@Override
	public ResourceLocation getModelLocation(PossessedSoldierEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/possessedsoldier.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PossessedSoldierEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PossessedSoldierEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessedsoldier.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PossessedSoldierEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}