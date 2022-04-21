package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.TotemItemModel;
import mod.azure.doom.item.DoomBlockItem;
import software.bernie.geckolib3q.renderers.geo.GeoItemRenderer;

public class TotemItemRender extends GeoItemRenderer<DoomBlockItem> {

	public TotemItemRender() {
		super(new TotemItemModel());
	}

}