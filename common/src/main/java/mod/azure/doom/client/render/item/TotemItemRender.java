package mod.azure.doom.client.render.item;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.items.TotemItemModel;
import mod.azure.doom.items.blockitems.DoomBlockItem;

public class TotemItemRender extends GeoItemRenderer<DoomBlockItem> {

    public TotemItemRender() {
        super(new TotemItemModel());
    }

}