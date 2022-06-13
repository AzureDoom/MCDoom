package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class FireBaronModel extends AnimatedTickingGeoModel<FireBaronEntity> {

	private static final Identifier[] TEX = { new Identifier(DoomMod.MODID, "textures/entity/firebaron.png"),
			new Identifier(DoomMod.MODID, "textures/entity/firebaron_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/firebaron_2.png") };

	@Override
	public Identifier getModelResource(FireBaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(FireBaronEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationResource(FireBaronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron2016.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(FireBaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX((extraData.headPitch + 20) * ((float) Math.PI / 360F));
			head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
		}
	}
}