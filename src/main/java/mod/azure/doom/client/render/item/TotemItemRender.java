package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.TotemItemModel;
import mod.azure.doom.item.TotemBlockItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class TotemItemRender extends GeoItemRenderer<TotemBlockItem> {

	public TotemItemRender() {
		super(new TotemItemModel());
	}

}