package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.DarkLordCrucibleModel;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class DarkLordCrucibleRender extends GeoItemRenderer<DarkLordCrucibleItem> {
	public DarkLordCrucibleRender() {
		super(new DarkLordCrucibleModel());
	}
}