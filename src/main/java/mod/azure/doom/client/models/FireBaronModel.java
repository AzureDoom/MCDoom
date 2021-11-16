package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FireBaronModel extends AnimatedTickingGeoModel<FireBaronEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/firebaron.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/firebaron_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/firebaron_2.png") };

	@Override
	public ResourceLocation getModelLocation(FireBaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(FireBaronEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationFileLocation(FireBaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/baron2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(FireBaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}