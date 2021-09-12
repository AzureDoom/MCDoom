package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.RocketLauncher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketLauncherModel extends AnimatedGeoModel<RocketLauncher> {
	@Override
	public ResourceLocation getModelLocation(RocketLauncher object) {
		return new ResourceLocation(DoomMod.MODID, "geo/rocketlauncher.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RocketLauncher object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/rocketlauncher.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(RocketLauncher animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocketlauncher.animation.json");
	}
}
