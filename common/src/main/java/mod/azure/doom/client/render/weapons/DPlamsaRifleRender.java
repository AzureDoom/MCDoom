package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.DPlamsaRifleModel;
import mod.azure.doom.items.weapons.DPlasmaRifle;

public class DPlamsaRifleRender extends GeoItemRenderer<DPlasmaRifle> {
	public DPlamsaRifleRender() {
		super(new DPlamsaRifleModel());
	}
}