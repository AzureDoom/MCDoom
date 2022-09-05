package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ZombiemanModel extends AnimatedTickingGeoModel<ZombiemanEntity> {

	@Override
	public ResourceLocation getModelResource(ZombiemanEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ZombiemanEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/eternalzombiemen.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ZombiemanEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/shotgunzombie.animation.json");
	}

	@Override
	public void setLivingAnimations(ZombiemanEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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