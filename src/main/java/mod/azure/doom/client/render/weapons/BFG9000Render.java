package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.BFG9000Model;
import mod.azure.doom.item.weapons.BFG9000;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class BFG9000Render extends GeoItemRenderer<BFG9000> {
	public BFG9000Render() {
		super(new BFG9000Model());
	}
}