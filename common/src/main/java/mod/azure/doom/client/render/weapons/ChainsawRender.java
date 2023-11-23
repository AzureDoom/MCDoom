package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.ChainsawModel;
import mod.azure.doom.items.weapons.ChainsawAnimated;

public class ChainsawRender extends GeoItemRenderer<ChainsawAnimated> {

	public ChainsawRender() {
		super(new ChainsawModel());
	}
}