package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.PistolModel;
import mod.azure.doom.item.weapons.PistolItem;

public class PistolRender extends GeoItemRenderer<PistolItem> {
	public PistolRender() {
		super(new PistolModel());
	}
}