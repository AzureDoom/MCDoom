package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class DPlamsaRifleModel extends GeoModel<DPlasmaRifle> {
	@Override
	public ResourceLocation getModelResource(DPlasmaRifle object) {
		return DoomMod.modResource("geo/doomed_plasma_rifle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DPlasmaRifle object) {
		return DoomMod.modResource("textures/item/doomed_plasma_rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DPlasmaRifle animatable) {
		return DoomMod.modResource("animations/doomed_plasma_rifle.animation.json");
	}
}
