package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DGauss;
import net.minecraft.resources.ResourceLocation;

public class DGaussModel extends GeoModel<DGauss> {
	@Override
	public ResourceLocation getModelResource(DGauss object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_gauss_cannon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DGauss object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/doomed_gauss_cannon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DGauss animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_gauss_cannon.animation.json");
	}
}
