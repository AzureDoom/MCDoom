package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.GunCraftingItemModel;
import mod.azure.doom.item.GuntableBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GunCraftingItemRender extends GeoItemRenderer<GuntableBlockItem> {

	public GunCraftingItemRender() {
		super(new GunCraftingItemModel());
	}

}