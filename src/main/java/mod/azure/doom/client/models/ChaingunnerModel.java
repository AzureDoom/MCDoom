package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ChaingunnerModel extends AnimatedTickingGeoModel<ChaingunnerEntity> {

	public ChaingunnerModel() {
	}

	@Override
	public ResourceLocation getModelLocation(ChaingunnerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ChaingunnerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/chaingunner.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ChaingunnerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/chaingunner.animation.json");
	}

	@Override
	public void setLivingAnimations(ChaingunnerEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bipedHead");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}
}