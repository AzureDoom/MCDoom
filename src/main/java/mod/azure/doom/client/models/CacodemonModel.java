package mod.azure.doom.client.models;

import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CacodemonModel extends AnimatedTickingGeoModel<CacodemonEntity> {

	@Override
	public ResourceLocation getModelLocation(CacodemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/" + (object.getVariant() == 1 ? "cacodemon64"
				: object.getVariant() >= 3 ? "cacodemoneternal" : "cacodemon") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CacodemonEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 1 ? "cacodemon64"
						: object.getVariant() == 3 ? "cacodemoneternal"
								: object.getVariant() == 4 ? "cacodemon2016" : "cacodemon")
						+ ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CacodemonEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "cacodemoneternal." : "cacodemon_") + "animation.json");
	}

	@Override
	public void setLivingAnimations(CacodemonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("body");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}
}