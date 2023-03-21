package mod.azure.doom.client.render.item;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.items.GunCraftingItemModel;
import mod.azure.doom.item.GuntableBlockItem;

public class GunCraftingItemRender extends GeoItemRenderer<GuntableBlockItem> {

	public GunCraftingItemRender() {
		super(new GunCraftingItemModel());
	}

}