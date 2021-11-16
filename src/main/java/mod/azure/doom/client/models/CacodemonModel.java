package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CacodemonModel extends AnimatedTickingGeoModel<CacodemonEntity> {

	public ResourceLocation classic_model = new ResourceLocation(DoomMod.MODID, "geo/cacodemon.geo.json");
	public ResourceLocation doom64_model = new ResourceLocation(DoomMod.MODID, "geo/cacodemon64.geo.json");
	public ResourceLocation classic_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/cacodemon.png");
	public ResourceLocation doom64_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/cacodemon64.png");

	@Override
	public ResourceLocation getModelLocation(CacodemonEntity object) {
		return object.getVariant() == 1 ? classic_model : doom64_model;
	}

	@Override
	public ResourceLocation getTextureLocation(CacodemonEntity object) {
		return object.getVariant() == 1 ? classic_texture : doom64_texture;
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CacodemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/cacodemon_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(CacodemonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("body");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}