package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.RocketEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketModel extends AnimatedGeoModel<RocketEntity> {
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
}
