package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketMobModel extends AnimatedGeoModel<RocketMobEntity> {
	@Override
	public ResourceLocation getModelResource(RocketMobEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketMobEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/rocket.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketMobEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocket.animation.json");
	}
}
