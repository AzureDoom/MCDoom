package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnergyModel extends AnimatedGeoModel<EnergyCellEntity> {
	@Override
	public Identifier getModelLocation(EnergyCellEntity object) {
		return new Identifier(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public Identifier getTextureLocation(EnergyCellEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/plasma_ball.png");
	}

	@Override
	public Identifier getAnimationFileLocation(EnergyCellEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
