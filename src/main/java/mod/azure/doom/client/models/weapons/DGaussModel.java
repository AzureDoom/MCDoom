package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DGauss;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DGaussModel extends GeoModel<DGauss> {
	@Override
	public Identifier getModelResource(DGauss object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_gauss_cannon.geo.json");
	}

	@Override
	public Identifier getTextureResource(DGauss object) {
		return new Identifier(DoomMod.MODID, "textures/item/doomed_gauss_cannon.png");
	}

	@Override
	public Identifier getAnimationResource(DGauss animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_gauss_cannon.animation.json");
	}
}
