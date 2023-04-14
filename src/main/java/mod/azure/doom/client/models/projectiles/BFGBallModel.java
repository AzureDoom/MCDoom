package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BFGBallModel extends GeoModel<BFGEntity> {
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

	@Override
	public RenderType getRenderType(BFGEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
