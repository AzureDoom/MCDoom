package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpModel extends AnimatedTickingGeoModel<ImpEntity> {

	public Identifier classic_model = new Identifier(DoomMod.MODID, "geo/imp.geo.json");
	public Identifier nightmareimp_model = new Identifier(DoomMod.MODID, "geo/nightmareimp.geo.json");
	public Identifier imp2016_model = new Identifier(DoomMod.MODID, "geo/imp2016.geo.json");

	public Identifier classic_texture = new Identifier(DoomMod.MODID, "textures/entity/imp-texturemap.png");
	public Identifier d64_texture = new Identifier(DoomMod.MODID, "textures/entity/imp-64.png");
	public Identifier nightmareimp_texture = new Identifier(DoomMod.MODID, "textures/entity/nightmareimp-texture.png");
	public Identifier imp2016_texture = new Identifier(DoomMod.MODID, "textures/entity/imp2016.png");

	public Identifier imp2016_animation = new Identifier(DoomMod.MODID, "animations/imp2016.animation.json");
	public Identifier imp_animation = new Identifier(DoomMod.MODID, "animations/imp_animation.json");

	@Override
	public Identifier getModelResource(ImpEntity object) {
		return object.getVariant() == 2 ? nightmareimp_model
				: object.getVariant() == 3 ? nightmareimp_model
						: object.getVariant() == 4 ? imp2016_model : classic_model;
	}

	@Override
	public Identifier getTextureResource(ImpEntity object) {
		return object.getVariant() == 2 ? nightmareimp_texture
				: object.getVariant() == 3 ? d64_texture : object.getVariant() == 4 ? imp2016_texture : classic_texture;
	}

	@Override
	public Identifier getAnimationResource(ImpEntity object) {
		return object.getVariant() == 4 ? imp2016_animation : imp_animation;
	}

	@Override
	public void setLivingAnimations(ImpEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion((extraData.headPitch - 5) * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}