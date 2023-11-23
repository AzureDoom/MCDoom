package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.DShotgun;
import net.minecraft.resources.ResourceLocation;

public class DSGModel extends GeoModel<DShotgun> {
    @Override
    public ResourceLocation getModelResource(DShotgun object) {
        return MCDoom.modResource("geo/doomed_shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DShotgun object) {
        return MCDoom.modResource("textures/item/doomed_shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DShotgun animatable) {
        return MCDoom.modResource("animations/doomed_shotgun.animation.json");
    }
}
