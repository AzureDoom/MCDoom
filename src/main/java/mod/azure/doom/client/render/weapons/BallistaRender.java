package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.BallistaModel;
import mod.azure.doom.item.weapons.Ballista;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class BallistaRender extends GeoItemRenderer<Ballista> {
	public BallistaRender() {
		super(new BallistaModel());
	}
}