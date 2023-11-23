package mod.azure.doom.client.models.items;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.blockitems.GuntableBlockItem;
import net.minecraft.resources.ResourceLocation;

public class GunCraftingItemModel extends GeoModel<GuntableBlockItem> {
    @Override
    public ResourceLocation getAnimationResource(GuntableBlockItem entity) {
        return MCDoom.modResource("animations/gun_table.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(GuntableBlockItem animatable) {
        return MCDoom.modResource("geo/gun_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GuntableBlockItem entity) {
        return MCDoom.modResource("textures/block/gun_table.png");
    }
}
