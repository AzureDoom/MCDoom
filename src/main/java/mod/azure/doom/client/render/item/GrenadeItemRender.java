package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.GrenadeItemModel;
import mod.azure.doom.item.weapons.GrenadeItem;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class GrenadeItemRender extends GeoItemRenderer<GrenadeItem> {

	public GrenadeItemRender() {
		super(new GrenadeItemModel());
	}

}