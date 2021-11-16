package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HellknightModel extends AnimatedTickingGeoModel<HellknightEntity> {

	public HellknightModel() {
	}

	@Override
	public ResourceLocation getModelLocation(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/hellknight.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/hellknight-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(HellknightEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}