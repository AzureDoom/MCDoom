package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.Unmaker;
import net.minecraft.resources.ResourceLocation;

public class UnmakerModel extends GeoModel<Unmaker> {
    @Override
    public ResourceLocation getModelResource(Unmaker object) {
        return MCDoom.modResource("geo/unmaykr.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Unmaker object) {
        return MCDoom.modResource("textures/item/unmaker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Unmaker animatable) {
        return MCDoom.modResource("animations/unmaykr.animation.json");
    }
}
