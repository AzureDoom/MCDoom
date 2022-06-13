package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ArmoredBaronModel extends AnimatedTickingGeoModel<ArmoredBaronEntity> {

	@Override
	public Identifier getModelResource(ArmoredBaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(ArmoredBaronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/armoredbaron.png");
	}

	@Override
	public Identifier getAnimationResource(ArmoredBaronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(ArmoredBaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}