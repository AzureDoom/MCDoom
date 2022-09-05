package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MarauderModel extends AnimatedTickingGeoModel<MarauderEntity> {

	@Override
	public ResourceLocation getModelResource(MarauderEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/marauder.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MarauderEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/marauder.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MarauderEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/marauder.animation.json");
	}

	@Override
	public void setLivingAnimations(MarauderEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}
}