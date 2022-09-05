package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Baron2016Model extends AnimatedTickingGeoModel<BaronEntity> {

	@Override
	public Identifier getModelResource(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/baron2016.png");
	}

	@Override
	public Identifier getAnimationResource(BaronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron2016.animation.json");
	}

	@Override
	public void setLivingAnimations(BaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
			head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
		}
	}
}