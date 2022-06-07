package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BFGBallModel extends AnimatedGeoModel<BFGEntity> {
	@Override
	public Identifier getModelResource(BFGEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bfg_ball.geo.json");
	}

	@Override
	public Identifier getTextureResource(BFGEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/bfg_ball.png");
	}

	@Override
	public Identifier getAnimationResource(BFGEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/bfgball.animation.json");
	}
}
