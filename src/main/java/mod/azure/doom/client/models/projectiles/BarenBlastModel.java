package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BarenBlastModel extends AnimatedGeoModel<BarenBlastEntity> {
	@Override
	public Identifier getModelLocation(BarenBlastEntity object) {
		return new Identifier(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BarenBlastEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/plasma_ball_red.png");
	}

	@Override
	public Identifier getAnimationFileLocation(BarenBlastEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
