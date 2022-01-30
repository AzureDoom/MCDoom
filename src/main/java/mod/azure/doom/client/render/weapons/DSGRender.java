package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.DSGModel;
import mod.azure.doom.item.weapons.DShotgun;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class DSGRender extends GeoItemRenderer<DShotgun> {
	public DSGRender() {
		super(new DSGModel());
	}
}