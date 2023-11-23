package mod.azure.doom.client.models.items;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.weapons.GrenadeItem;
import net.minecraft.resources.ResourceLocation;

public class GrenadeItemModel extends GeoModel<GrenadeItem> {
    @Override
    public ResourceLocation getModelResource(GrenadeItem object) {
        return MCDoom.modResource("geo/doomed_grenade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrenadeItem object) {
        return MCDoom.modResource("textures/item/doomed_grenade.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadeItem animatable) {
        return MCDoom.modResource("animations/doomed_grenade.animation.json");
    }
}
