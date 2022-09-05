package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MechaZombieModel extends AnimatedTickingGeoModel<MechaZombieEntity> {

	@Override
	public ResourceLocation getModelLocation(MechaZombieEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mechazombie.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MechaZombieEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/mechazombie.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MechaZombieEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/mechazombie_animation.json");
	}

	@Override
	public void setLivingAnimations(MechaZombieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 360F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j());
		}
	}
}