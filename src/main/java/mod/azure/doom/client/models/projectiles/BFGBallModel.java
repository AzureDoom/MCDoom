package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BFGBallModel extends GeoModel<BFGEntity> {
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
	
	@Override
	public RenderLayer getRenderType(BFGEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
