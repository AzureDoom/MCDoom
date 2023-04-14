package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import net.minecraft.resources.ResourceLocation;

public class DPlamsaRifleModel extends GeoModel<DPlasmaRifle> {
	@Override
	public ResourceLocation getModelResource(DPlasmaRifle object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_plasma_rifle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DPlasmaRifle object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/doomed_plasma_rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DPlasmaRifle animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_plasma_rifle.animation.json");
	}
}
