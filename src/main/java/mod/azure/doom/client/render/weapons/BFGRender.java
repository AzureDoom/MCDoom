package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.BFGModel;
import mod.azure.doom.item.weapons.BFG;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class BFGRender extends GeoItemRenderer<BFG> {
	public BFGRender() {
		super(new BFGModel());
	}
}