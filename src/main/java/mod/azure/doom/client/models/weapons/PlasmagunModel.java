package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PlasmaGun;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PlasmagunModel extends GeoModel<PlasmaGun> {
	@Override
	public ResourceLocation getModelResource(PlasmaGun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/plasmagun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlasmaGun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PlasmaGun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/plasmagun.animation.json");
	}
}
