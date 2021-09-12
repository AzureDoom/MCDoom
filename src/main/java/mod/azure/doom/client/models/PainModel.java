package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PainModel extends AnimatedGeoModel<PainEntity> {

	public ResourceLocation classic_model = new ResourceLocation(DoomMod.MODID, "geo/pain.geo.json");
	public ResourceLocation doom64_model = new ResourceLocation(DoomMod.MODID, "geo/pain64.geo.json");
	public ResourceLocation classic_texture = new ResourceLocation(DoomMod.MODID,
			"textures/entity/painelemental-normal.png");
	public ResourceLocation classic_texture_attacking = new ResourceLocation(DoomMod.MODID,
			"textures/entity/painelemental-attacking.png");
	public ResourceLocation doom64_texture = new ResourceLocation(DoomMod.MODID,
			"textures/entity/painelemental64-normal.png");
	public ResourceLocation doom64_texture_attacking = new ResourceLocation(DoomMod.MODID,
			"textures/entity/painelemental64-attacking.png");

	@Override
	public ResourceLocation getModelLocation(PainEntity object) {
		return object.getVariant() == 1 ? classic_model : doom64_model;
	}

	@Override
	public ResourceLocation getTextureLocation(PainEntity object) {
		return object.getVariant() == 1 ? (object.getAttckingState() == 1 ? classic_texture_attacking : classic_texture)
				: (object.getAttckingState() == 1 ? doom64_texture_attacking : doom64_texture);
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PainEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/pain_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PainEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("body");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}