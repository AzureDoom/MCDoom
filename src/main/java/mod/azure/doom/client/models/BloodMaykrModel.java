package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BloodMaykrModel extends AnimatedTickingGeoModel<BloodMaykrEntity> {

	@Override
	public ResourceLocation getModelResource(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bloodmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/bloodmaykr.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BloodMaykrEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/bloodmaykr.animation.json");
	}

	@Override
	public void setLivingAnimations(BloodMaykrEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}

}