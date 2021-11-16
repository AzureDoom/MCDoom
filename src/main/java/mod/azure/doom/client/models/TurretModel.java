package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TurretEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TurretModel extends AnimatedTickingGeoModel<TurretEntity> {

	@Override
	public ResourceLocation getModelLocation(TurretEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/turret.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TurretEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/turret.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TurretEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/turret.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(TurretEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("eye");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 360F));
	}
}