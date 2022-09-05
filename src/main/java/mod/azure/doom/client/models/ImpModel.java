package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpModel extends AnimatedTickingGeoModel<ImpEntity> {

	public ResourceLocation classic_model = new ResourceLocation(DoomMod.MODID, "geo/imp.geo.json");
	public ResourceLocation nightmareimp_model = new ResourceLocation(DoomMod.MODID, "geo/nightmareimp.geo.json");
	public ResourceLocation imp2016_model = new ResourceLocation(DoomMod.MODID, "geo/imp2016.geo.json");
	public ResourceLocation classic_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/imp-texturemap.png");
	public ResourceLocation nightmareimp_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/nightmareimp-texture.png");
	public ResourceLocation imp2016_texture = new ResourceLocation(DoomMod.MODID, "textures/entity/imp2016.png");
	public ResourceLocation imp2016_animation = new ResourceLocation(DoomMod.MODID, "animations/imp2016.animation.json");
	public ResourceLocation imp_animation = new ResourceLocation(DoomMod.MODID, "animations/imp_animation.json");

	@Override
	public ResourceLocation getModelResource(ImpEntity object) {
		return object.getVariant() == 2 ? nightmareimp_model : object.getVariant() == 3 ? imp2016_model : classic_model;
	}

	@Override
	public ResourceLocation getTextureResource(ImpEntity object) {
		return object.getVariant() == 2 ? nightmareimp_texture
				: object.getVariant() == 3 ? imp2016_texture : classic_texture;
	}

	@Override
	public ResourceLocation getAnimationResource(ImpEntity object) {
		return object.getVariant() == 3 ? imp2016_animation : imp_animation;
	}

	@Override
	public void setLivingAnimations(ImpEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation((extraData.headPitch - 5) * ((float) Math.PI / 180F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j());
		}
	}
}