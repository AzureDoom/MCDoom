package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.RocketEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketModel extends AnimatedGeoModel<RocketEntity> {
	@Override
	public ResourceLocation getModelResource(RocketEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/rocket.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocket.animation.json");
	}
}
