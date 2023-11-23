package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.RocketLauncher;
import net.minecraft.resources.ResourceLocation;

public class RocketLauncherModel extends GeoModel<RocketLauncher> {
    @Override
    public ResourceLocation getModelResource(RocketLauncher object) {
        return MCDoom.modResource("geo/rocketlauncher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketLauncher object) {
        return MCDoom.modResource("textures/item/rocketlauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketLauncher animatable) {
        return MCDoom.modResource("animations/rocketlauncher.animation.json");
    }
}
