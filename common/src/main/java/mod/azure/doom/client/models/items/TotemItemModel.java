package mod.azure.doom.client.models.items;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.blockitems.TotemBlockItem;
import net.minecraft.resources.ResourceLocation;

public class TotemItemModel extends GeoModel<TotemBlockItem> {
    @Override
    public ResourceLocation getAnimationResource(TotemBlockItem entity) {
        return MCDoom.modResource("animations/totem.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TotemBlockItem animatable) {
        return MCDoom.modResource("geo/totem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TotemBlockItem entity) {
        return MCDoom.modResource("textures/block/totem.png");
    }
}
