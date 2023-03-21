package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.RocketLauncher;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class RocketLauncherModel extends GeoModel<RocketLauncher> {
	@Override
	public ResourceLocation getModelResource(RocketLauncher object) {
		return DoomMod.modResource("geo/rocketlauncher.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketLauncher object) {
		return DoomMod.modResource("textures/item/rocketlauncher.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketLauncher animatable) {
		return DoomMod.modResource("animations/rocketlauncher.animation.json");
	}
}
