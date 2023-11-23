package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.DarkLordCrucibleModel;
import mod.azure.doom.items.weapons.DarkLordCrucibleItem;

public class DarkLordCrucibleRender extends GeoItemRenderer<DarkLordCrucibleItem> {
	public DarkLordCrucibleRender() {
		super(new DarkLordCrucibleModel());
	}
}