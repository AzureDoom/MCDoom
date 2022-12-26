package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.RocketLauncher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RocketLauncherModel extends GeoModel<RocketLauncher> {
	@Override
	public ResourceLocation getModelResource(RocketLauncher object) {
		return new ResourceLocation(DoomMod.MODID, "geo/rocketlauncher.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketLauncher object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/rocketlauncher.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketLauncher animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocketlauncher.animation.json");
	}
}
