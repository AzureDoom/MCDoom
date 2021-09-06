package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnergyMobModel extends AnimatedGeoModel<EnergyCellMobEntity> {
	@Override
	public Identifier getModelLocation(EnergyCellMobEntity object) {
		return new Identifier(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public Identifier getTextureLocation(EnergyCellMobEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/plasma_ball.png");
	}

	@Override
	public Identifier getAnimationFileLocation(EnergyCellMobEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
