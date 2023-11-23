package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.SGModel;
import mod.azure.doom.items.weapons.Shotgun;

public class SGRender extends GeoItemRenderer<Shotgun> {
	public SGRender() {
		super(new SGModel());
	}
}