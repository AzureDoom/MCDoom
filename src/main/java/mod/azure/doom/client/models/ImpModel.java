package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ImpModel extends GeoModel<ImpEntity> {

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
	public void setCustomAnimations(ImpEntity animatable, long instanceId, AnimationState<ImpEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() - 5) * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(ImpEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}