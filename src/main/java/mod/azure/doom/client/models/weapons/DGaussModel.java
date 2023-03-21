package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DGauss;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class DGaussModel extends GeoModel<DGauss> {
	@Override
	public ResourceLocation getModelResource(DGauss object) {
		return DoomMod.modResource("geo/doomed_gauss_cannon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DGauss object) {
		return DoomMod.modResource("textures/item/doomed_gauss_cannon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DGauss animatable) {
		return DoomMod.modResource("animations/doomed_gauss_cannon.animation.json");
	}
}
