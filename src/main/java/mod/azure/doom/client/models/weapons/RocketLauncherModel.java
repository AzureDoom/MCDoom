package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.RocketLauncher;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RocketLauncherModel extends GeoModel<RocketLauncher> {
	@Override
	public Identifier getModelResource(RocketLauncher object) {
		return new Identifier(DoomMod.MODID, "geo/rocketlauncher.geo.json");
	}

	@Override
	public Identifier getTextureResource(RocketLauncher object) {
		return new Identifier(DoomMod.MODID, "textures/item/rocketlauncher.png");
	}

	@Override
	public Identifier getAnimationResource(RocketLauncher animatable) {
		return new Identifier(DoomMod.MODID, "animations/rocketlauncher.animation.json");
	}
}
