package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.UnmakerModel;
import mod.azure.doom.items.weapons.Unmaker;

public class UnmakerRender extends GeoItemRenderer<Unmaker> {
	public UnmakerRender() {
		super(new UnmakerModel());
	}
}