package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.DGaussModel;
import mod.azure.doom.items.weapons.DGauss;

public class DGaussRender extends GeoItemRenderer<DGauss> {
	public DGaussRender() {
		super(new DGaussModel());
	}
}