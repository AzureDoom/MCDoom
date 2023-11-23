package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.ChaingunModel;
import mod.azure.doom.items.weapons.Chaingun;

public class ChaingunRender extends GeoItemRenderer<Chaingun> {
	public ChaingunRender() {
		super(new ChaingunModel());
	}
}