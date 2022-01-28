package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DGauss;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DGaussModel extends AnimatedGeoModel<DGauss> {
	@Override
	public Identifier getModelLocation(DGauss object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_gauss_cannon.geo.json");
	}

	@Override
	public Identifier getTextureLocation(DGauss object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_gauss_cannon.png");
	}

	@Override
	public Identifier getAnimationFileLocation(DGauss animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_gauss_cannon.animation.json");
	}
}
