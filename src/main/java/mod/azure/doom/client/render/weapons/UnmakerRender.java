package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.UnmakerModel;
import mod.azure.doom.item.weapons.Unmaker;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class UnmakerRender extends GeoItemRenderer<Unmaker> {
	public UnmakerRender() {
		super(new UnmakerModel());
	}
}