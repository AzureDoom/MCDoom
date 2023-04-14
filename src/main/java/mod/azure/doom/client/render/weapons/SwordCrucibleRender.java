package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.SwordCrucibleModel;
import mod.azure.doom.item.weapons.SwordCrucibleItem;

public class SwordCrucibleRender extends GeoItemRenderer<SwordCrucibleItem> {
	public SwordCrucibleRender() {
		super(new SwordCrucibleModel());
	}
}