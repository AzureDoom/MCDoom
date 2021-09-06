package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RevenantModel extends AnimatedGeoModel<RevenantEntity> {

	public RevenantModel() {
	}

	@Override
	public ResourceLocation getModelLocation(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/revenant.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(RevenantEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}