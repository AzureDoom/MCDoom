package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.Unmaykr;
import net.minecraft.resources.ResourceLocation;

public class UnmaykrModel extends GeoModel<Unmaykr> {
    @Override
    public ResourceLocation getModelResource(Unmaykr object) {
        return MCDoom.modResource("geo/unmaykr.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Unmaykr object) {
        return MCDoom.modResource("textures/item/unmaykr.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Unmaykr animatable) {
        return MCDoom.modResource("animations/unmaykr.animation.json");
    }
}
