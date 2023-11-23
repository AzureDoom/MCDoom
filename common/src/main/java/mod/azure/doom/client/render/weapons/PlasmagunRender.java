package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.PlasmagunModel;
import mod.azure.doom.items.weapons.PlasmaGun;

public class PlasmagunRender extends GeoItemRenderer<PlasmaGun> {
	public PlasmagunRender() {
		super(new PlasmagunModel());
	}
}