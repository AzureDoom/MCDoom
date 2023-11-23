package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.PlasmaGun;
import net.minecraft.resources.ResourceLocation;

public class PlasmagunModel extends GeoModel<PlasmaGun> {
    @Override
    public ResourceLocation getModelResource(PlasmaGun object) {
        return MCDoom.modResource("geo/plasmagun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PlasmaGun object) {
        return MCDoom.modResource("textures/item/rifle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PlasmaGun animatable) {
        return MCDoom.modResource("animations/plasmagun.animation.json");
    }
}
