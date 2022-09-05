package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TentacleModel extends AnimatedTickingGeoModel<TentacleEntity> {

	@Override
	public ResourceLocation getModelResource(TentacleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/tentacle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TentacleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/tentacle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TentacleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/tentacle.animation.json");
	}

	@Override
	public void setLivingAnimations(TentacleEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("bone3");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP
					.rotation((extraData.headPitch - 30) * ((float) Math.PI / 360F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 360F)).j());
		}
	}
}