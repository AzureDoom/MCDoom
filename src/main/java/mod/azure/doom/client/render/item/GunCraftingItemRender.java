package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.GunCraftingItemModel;
import mod.azure.doom.item.TotemBlockItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GunCraftingItemRender extends GeoItemRenderer<TotemBlockItem> {

	public GunCraftingItemRender() {
		super(new GunCraftingItemModel());
	}

}