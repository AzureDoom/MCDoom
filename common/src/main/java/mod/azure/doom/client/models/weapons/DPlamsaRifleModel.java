package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.DPlasmaRifle;
import net.minecraft.resources.ResourceLocation;

public class DPlamsaRifleModel extends GeoModel<DPlasmaRifle> {
    @Override
    public ResourceLocation getModelResource(DPlasmaRifle object) {
        return MCDoom.modResource("geo/doomed_plasma_rifle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DPlasmaRifle object) {
        return MCDoom.modResource("textures/item/doomed_plasma_rifle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DPlasmaRifle animatable) {
        return MCDoom.modResource("animations/doomed_plasma_rifle.animation.json");
    }
}
