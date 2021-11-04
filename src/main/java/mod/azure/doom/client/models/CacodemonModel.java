package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CacodemonModel extends AnimatedTickingGeoModel<CacodemonEntity> {

	public Identifier classic_model = new Identifier(DoomMod.MODID, "geo/cacodemon.geo.json");
	public Identifier doom64_model = new Identifier(DoomMod.MODID, "geo/cacodemon64.geo.json");
	public Identifier classic_texture = new Identifier(DoomMod.MODID, "textures/entity/cacodemon.png");
	public Identifier doom64_texture = new Identifier(DoomMod.MODID, "textures/entity/cacodemon64.png");

	@Override
	public Identifier getModelLocation(CacodemonEntity object) {
		return object.getVariant() == 1 ? classic_model : doom64_model;
	}

	@Override
	public Identifier getTextureLocation(CacodemonEntity object) {
		return object.getVariant() == 1 ? classic_texture : doom64_texture;
	}

	@Override
	public Identifier getAnimationFileLocation(CacodemonEntity object) {
		return new Identifier(DoomMod.MODID, "animations/cacodemon_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(CacodemonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("body");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}
}