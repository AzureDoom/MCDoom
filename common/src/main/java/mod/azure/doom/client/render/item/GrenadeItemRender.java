package mod.azure.doom.client.render.item;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.items.GrenadeItemModel;
import mod.azure.doom.items.weapons.GrenadeItem;

public class GrenadeItemRender extends GeoItemRenderer<GrenadeItem> {

    public GrenadeItemRender() {
        super(new GrenadeItemModel());
    }

}