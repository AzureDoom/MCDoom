package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BaronModel extends AnimatedTickingGeoModel<BaronEntity> {

	public BaronModel() {
	}

	@Override
	public ResourceLocation getModelLocation(BaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/baron.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/baronofhell-"
				+ (object.getVariant() == 2 ? "green" : object.getVariant() == 3 ? "64" : "texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BaronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@Override
	public void setLivingAnimations(BaronEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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