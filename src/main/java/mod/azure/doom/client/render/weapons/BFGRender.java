package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.BFGModel;
import mod.azure.doom.item.weapons.BFG;

public class BFGRender extends GeoItemRenderer<BFG> {
	public BFGRender() {
		super(new BFGModel());
	}
}