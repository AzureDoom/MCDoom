package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DGauss;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DGaussModel extends AnimatedGeoModel<DGauss> {
	@Override
	public ResourceLocation getModelResource(DGauss object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_gauss_cannon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DGauss object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/doomed_gauss_cannon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DGauss animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_gauss_cannon.animation.json");
	}
}
