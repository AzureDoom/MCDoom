package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.DGauss;
import net.minecraft.resources.ResourceLocation;

public class DGaussModel extends GeoModel<DGauss> {
    @Override
    public ResourceLocation getModelResource(DGauss object) {
        return MCDoom.modResource("geo/doomed_gauss_cannon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DGauss object) {
        return MCDoom.modResource("textures/item/doomed_gauss_cannon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DGauss animatable) {
        return MCDoom.modResource("animations/doomed_gauss_cannon.animation.json");
    }
}
