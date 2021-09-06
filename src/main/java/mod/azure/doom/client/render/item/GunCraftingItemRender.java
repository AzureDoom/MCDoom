package mod.azure.doom.client.render.item;

import mod.azure.doom.client.models.items.GunCraftingItemModel;
import mod.azure.doom.item.DoomBlockItem;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class GunCraftingItemRender extends GeoItemRenderer<DoomBlockItem> {

	public GunCraftingItemRender() {
		super(new GunCraftingItemModel());
	}

}