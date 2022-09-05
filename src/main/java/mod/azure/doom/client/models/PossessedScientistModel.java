package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedScientistModel extends AnimatedTickingGeoModel<PossessedScientistEntity> {

	@Override
	public ResourceLocation getModelResource(PossessedScientistEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/scientistpossessed.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PossessedScientistEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/possessedscientist.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PossessedScientistEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessed_scientist_animation.json");
	}

	@Override
	public void setLivingAnimations(PossessedScientistEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 360F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j());
		}
	}
}