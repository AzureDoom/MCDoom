package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketMobModel extends AnimatedGeoModel<RocketMobEntity> {
	@Override
	public Identifier getModelLocation(RocketMobEntity object) {
		return new Identifier(DoomMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public Identifier getTextureLocation(RocketMobEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/rocket.png");
	}

	@Override
	public Identifier getAnimationFileLocation(RocketMobEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/rocket.animation.json");
	}
}
