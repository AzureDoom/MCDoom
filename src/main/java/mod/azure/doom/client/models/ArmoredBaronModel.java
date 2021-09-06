package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ArmoredBaronModel extends AnimatedGeoModel<ArmoredBaronEntity> {

	@Override
	public Identifier getModelLocation(ArmoredBaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ArmoredBaronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/armoredbaron.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ArmoredBaronEntity object) {
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