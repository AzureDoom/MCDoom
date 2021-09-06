package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PainModel extends AnimatedGeoModel<PainEntity> {

	public Identifier classic_model = new Identifier(DoomMod.MODID, "geo/pain.geo.json");
	public Identifier doom64_model = new Identifier(DoomMod.MODID, "geo/pain64.geo.json");
	public Identifier classic_texture = new Identifier(DoomMod.MODID, "textures/entity/painelemental-normal.png");
	public Identifier classic_texture_attacking = new Identifier(DoomMod.MODID,
			"textures/entity/painelemental-attacking.png");
	public Identifier doom64_texture = new Identifier(DoomMod.MODID, "textures/entity/painelemental64-normal.png");
	public Identifier doom64_texture_attacking = new Identifier(DoomMod.MODID,
			"textures/entity/painelemental64-attacking.png");

	@Override
	public Identifier getModelLocation(PainEntity object) {
		return object.getVariant() == 1 ? classic_model : doom64_model;
	}

	@Override
	public Identifier getTextureLocation(PainEntity object) {
		return object.getVariant() == 1 ? (object.getAttckingState() == 1 ? classic_texture_attacking : classic_texture)
				: (object.getAttckingState() == 1 ? doom64_texture_attacking : doom64_texture);
	}

	@Override
	public Identifier getAnimationFileLocation(PainEntity object) {
		return new Identifier(DoomMod.MODID, "animations/pain_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PainEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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