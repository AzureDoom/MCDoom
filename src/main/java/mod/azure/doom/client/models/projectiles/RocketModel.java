package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.RocketEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RocketModel extends GeoModel<RocketEntity> {
	@Override
	public Identifier getModelResource(RocketEntity object) {
		return new Identifier(DoomMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public Identifier getTextureResource(RocketEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/rocket.png");
	}

	@Override
	public Identifier getAnimationResource(RocketEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/rocket.animation.json");
	}

	@Override
	public RenderLayer getRenderType(RocketEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
