package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.BallistaModel;
import mod.azure.doom.items.weapons.Ballista;

public class BallistaRender extends GeoItemRenderer<Ballista> {
	public BallistaRender() {
		super(new BallistaModel());
	}
}