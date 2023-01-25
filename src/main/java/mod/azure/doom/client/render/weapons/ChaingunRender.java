package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.ChaingunModel;
import mod.azure.doom.item.weapons.Chaingun;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class ChaingunRender extends GeoItemRenderer<Chaingun> {
	public ChaingunRender() {
		super(new ChaingunModel());
	}
}