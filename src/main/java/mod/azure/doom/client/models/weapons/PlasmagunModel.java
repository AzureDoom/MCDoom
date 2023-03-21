package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PlasmaGun;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PlasmagunModel extends GeoModel<PlasmaGun> {
	@Override
	public ResourceLocation getModelResource(PlasmaGun object) {
		return DoomMod.modResource("geo/plasmagun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlasmaGun object) {
		return DoomMod.modResource("textures/item/rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PlasmaGun animatable) {
		return DoomMod.modResource("animations/plasmagun.animation.json");
	}
}
