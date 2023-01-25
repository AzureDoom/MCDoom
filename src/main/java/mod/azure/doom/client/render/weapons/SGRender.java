package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.SGModel;
import mod.azure.doom.item.weapons.Shotgun;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class SGRender extends GeoItemRenderer<Shotgun> {
	public SGRender() {
		super(new SGModel());
	}
}