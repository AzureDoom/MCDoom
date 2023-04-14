package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.DSGModel;
import mod.azure.doom.item.weapons.DShotgun;

public class DSGRender extends GeoItemRenderer<DShotgun> {
	public DSGRender() {
		super(new DSGModel());
	}
}