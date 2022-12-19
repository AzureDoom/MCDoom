package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.TotemItemModel;
import mod.azure.doom.item.TotemBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TotemItemRender extends GeoItemRenderer<TotemBlockItem> {

	public TotemItemRender() {
		super(new TotemItemModel());
	}

}