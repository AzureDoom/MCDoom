package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BFGBallModel extends AnimatedGeoModel<BFGEntity> {
	@Override
	public ResourceLocation getModelLocation(BFGEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bfg_ball.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BFGEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/bfg_ball.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BFGEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/bfgball.animation.json");
	}
}
