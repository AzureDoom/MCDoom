package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.PistolItem;
import net.minecraft.resources.ResourceLocation;

public class PistolModel extends GeoModel<PistolItem> {
    @Override
    public ResourceLocation getModelResource(PistolItem object) {
        return MCDoom.modResource("geo/pistol.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PistolItem object) {
        return MCDoom.modResource("textures/item/pistol.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PistolItem animatable) {
        return MCDoom.modResource("animations/pistol.animation.json");
    }
}
