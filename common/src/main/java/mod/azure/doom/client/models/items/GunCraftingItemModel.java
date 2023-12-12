package mod.azure.doom.client.models.items;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.blockitems.DoomBlockItem;
import net.minecraft.resources.ResourceLocation;

public class GunCraftingItemModel extends GeoModel<DoomBlockItem> {
    @Override
    public ResourceLocation getAnimationResource(DoomBlockItem entity) {
        return MCDoom.modResource("animations/gun_table.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(DoomBlockItem animatable) {
        return MCDoom.modResource("geo/gun_table.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DoomBlockItem entity) {
        return MCDoom.modResource("textures/block/gun_table.png");
    }
}
