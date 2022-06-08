package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BFGBallModel extends AnimatedGeoModel<BFGEntity> {
	@Override
	public ResourceLocation getModelResource(BFGEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bfg_ball.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BFGEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/bfg_ball.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BFGEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/bfgball.animation.json");
	}
}
